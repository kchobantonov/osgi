/*
 * Copyright (c) OSGi Alliance (2012). All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.osgi.test.cases.subsystem.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.namespace.BundleNamespace;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.resource.Resource;
import org.osgi.service.subsystem.Subsystem;
import org.osgi.service.subsystem.SubsystemConstants;


public class DependencySubsystemTests extends SubsystemTest{

	// TestPlan item 4A application
	public void test4A_application() {
		doTest4A(SUBSYSTEM_4A_APPLICATION);
	}

	// TestPlan item 4A composites
	public void test4A_composite() {
		doTest4A(SUBSYSTEM_4A_COMPOSITE);
	}

	// TestPlan item 4A features
	public void test4A_feature() {
		doTest4A(SUBSYSTEM_4A_FEATURE);
	}

	private void doTest4A(String subsystemName) {
		registerRepository(REPOSITORY_1);
		Subsystem root = getRootSubsystem();
		Collection<Resource> origRootConstituents = root.getConstituents();

		Subsystem subsystem = doSubsystemInstall(getName(), root, getName(), subsystemName, false);

		Collection<Resource> constituents = subsystem.getConstituents();
		assertNotNull("Null constituents.", constituents);
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(subsystem.getType())) {
			// there should be 5 (A, B, C, D, E) bundles
			assertEquals("Wrong number of constituents.", 5, constituents.size());
		} else {
			// there should be the context + 6 (A, B, C, D, E) bundles
			assertEquals("Wrong number of constituents.", 6, constituents.size());
		}

		Bundle a = getBundle(subsystem, BUNDLE_SHARE_A);
		Bundle b = getBundle(subsystem, BUNDLE_SHARE_B);
		Bundle c = getBundle(subsystem, BUNDLE_SHARE_C);
		Bundle d = getBundle(subsystem, BUNDLE_SHARE_D);
		Bundle e = getBundle(subsystem, BUNDLE_SHARE_E);

		doSubsystemOperation("start application", subsystem, Operation.START, false);
		for (Bundle bundle : new Bundle[] {a, b, c, d, e} ) {
			assertEquals("Wrong state for the bundle: " + bundle.getSymbolicName(), Bundle.ACTIVE, bundle.getState());
		}
		checkWiring(a, a, b, c, d, e);

		Collection<Resource> newRootconstituents = new ArrayList<Resource>(root.getConstituents());
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(subsystem.getType())) {
			newRootconstituents.removeAll(constituents);
		}
		newRootconstituents.removeAll(origRootConstituents);
		assertEquals("Found unexpected root constituents", 1, newRootconstituents.size());
		checkSubsystemConstituents("The subsystem is not a constituent of its parent.", Arrays.asList(subsystem), newRootconstituents);

	}

	// TestPlan item 4B application
	public void test4B_application() {
		doTest4B(SUBSYSTEM_4B_APPLICATION);
	}

	// TestPlan item 4B composites
	public void test4B_composite() {
		doTest4B(SUBSYSTEM_4B_COMPOSITE);
	}

	// TestPlan item 4B features
	public void test4B_feature() {
		doTest4B(SUBSYSTEM_4B_FEATURE);
	}

	private void doTest4B(String subsystemName) {
		registerRepository(REPOSITORY_2);
		Subsystem root = getRootSubsystem();
		Subsystem subsystem = doSubsystemInstall(getName(), root, getName(), subsystemName, false);

		Collection<Resource> constituents = subsystem.getConstituents();
		assertNotNull("Null constituents.", constituents);
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(subsystem.getType())) {
			// there should be 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 3, constituents.size());
		} else {
			// there should be the context + 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 4, constituents.size());
		}

		Bundle a = getBundle(root, BUNDLE_SHARE_A);
		Bundle b = getBundle(root, BUNDLE_SHARE_B);
		Bundle c = getBundle(subsystem, BUNDLE_SHARE_C);
		Bundle d = getBundle(subsystem, BUNDLE_SHARE_D);
		Bundle e = getBundle(subsystem, BUNDLE_SHARE_E);

		doSubsystemOperation("start application", subsystem, Operation.START, false);
		for (Bundle bundle : new Bundle[] {a, b, c, d, e} ) {
			assertEquals("Wrong state for the bundle: " + bundle.getSymbolicName(), Bundle.ACTIVE, bundle.getState());
		}
		checkWiring(a, a, b, c, d, e);
	}

	// TestPlan item 4C application + application
	public void test4C_application1() {
		doTest4C(SUBSYSTEM_4C_APPLICATION, SUBSYSTEM_4B_APPLICATION);
	}

	// TestPlan item 4C application + composite
	public void test4C_application2() {
		doTest4C(SUBSYSTEM_4C_APPLICATION, SUBSYSTEM_4B_COMPOSITE);
	}
	// TestPlan item 4C application + feature
	public void test4C_application3() {
		doTest4C(SUBSYSTEM_4C_APPLICATION, SUBSYSTEM_4B_FEATURE);
	}

	// TestPlan item 4C composite + application
	public void test4C_composite1() {
		doTest4C(SUBSYSTEM_4C_COMPOSITE, SUBSYSTEM_4B_APPLICATION);
	}

	// TestPlan item 4C composite + composite
	public void test4C_composite2() {
		doTest4C(SUBSYSTEM_4C_COMPOSITE, SUBSYSTEM_4B_COMPOSITE);
	}
	// TestPlan item 4C composite + feature
	public void test4C_composite3() {
		doTest4C(SUBSYSTEM_4C_APPLICATION, SUBSYSTEM_4B_FEATURE);
	}

	private void doTest4C(String subsystemName1, String subsystemName2) {
		registerRepository(REPOSITORY_1);
		Subsystem root = getRootSubsystem();
		Collection<Resource> origRootConstituents = root.getConstituents();

		Subsystem subsystem1 = doSubsystemInstall(getName(), root, subsystemName1, subsystemName1, false);

		Collection<Resource> constituents1 = subsystem1.getConstituents();
		assertNotNull("Null constituents.", constituents1);
		// there should be the context + 2 (A, B) bundles
		assertEquals("Wrong number of constituents.", 3, constituents1.size());

		Subsystem subsystem2 = doSubsystemInstall(getName(), subsystem1, subsystemName2, subsystemName2, false);
		Collection<Resource> constituents2 = subsystem2.getConstituents();
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(subsystem2.getType())) {
			// there should be 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 3, constituents2.size());
		} else {
			// there should be the context + 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 4, constituents2.size());
		}

		Bundle a = getBundle(subsystem1, BUNDLE_SHARE_A);
		Bundle b = getBundle(subsystem1, BUNDLE_SHARE_B);
		Bundle c = getBundle(subsystem2, BUNDLE_SHARE_C);
		Bundle d = getBundle(subsystem2, BUNDLE_SHARE_D);
		Bundle e = getBundle(subsystem2, BUNDLE_SHARE_E);

		doSubsystemOperation("start application", subsystem1, Operation.START, false);
		for (Bundle bundle : new Bundle[] {a, b, c, d, e} ) {
			assertEquals("Wrong state for the bundle: " + bundle.getSymbolicName(), Bundle.ACTIVE, bundle.getState());
		}
		checkWiring(a, a, b, c, d, e);

		Collection<Resource> newRootconstituents = new ArrayList<Resource>(root.getConstituents());
		newRootconstituents.removeAll(origRootConstituents);
		assertEquals("Found unexpected root constituents", 1, newRootconstituents.size());
		checkSubsystemConstituents("The subsystem is not a constituent of its parent.", Arrays.asList(subsystem1), newRootconstituents);
	}

	// TestPlan item 4D application
	public void test4D_application() {
		doTest4D(SUBSYSTEM_4D_APPLICATION);
	}

	// TestPlan item 4D composites
	public void test4D_composite() {
		doTest4D(SUBSYSTEM_4D_COMPOSITE);
	}

	// TestPlan item 4D features
	public void test4D_feature() {
		doTest4D(SUBSYSTEM_4D_FEATURE);
	}

	private void doTest4D(String subsystemName) {
		registerRepository(REPOSITORY_2);
		Subsystem root = getRootSubsystem();
		Subsystem subsystem = doSubsystemInstall(getName(), root, getName(), subsystemName, false);

		Collection<Resource> constituents = subsystem.getConstituents();
		assertNotNull("Null constituents.", constituents);
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(subsystem.getType())) {
			// there should be 2 (C, E) bundles
			assertEquals("Wrong number of constituents.", 2, constituents.size());
		} else {
			// there should be the context + 2 (C, E) bundles
			assertEquals("Wrong number of constituents.", 3, constituents.size());
		}

		assertNoBundle(root, BUNDLE_SHARE_A);
		assertNoBundle(root, BUNDLE_SHARE_B);
		Bundle f = getBundle(root, BUNDLE_SHARE_F);
		Bundle g = getBundle(root, BUNDLE_SHARE_G);
		Bundle c = getBundle(subsystem, BUNDLE_SHARE_C);
		Bundle e = getBundle(subsystem, BUNDLE_SHARE_E);

		doSubsystemOperation("start application", subsystem, Operation.START, false);

		for (Bundle bundle : new Bundle[] {f, g, c, e} ) {
			assertEquals("Wrong state for the bundle: " + bundle.getSymbolicName(), Bundle.ACTIVE, bundle.getState());
		}
		checkWiring(f, f, g, c, null, e);
	}

	// TestPlan item 4E1a application
	public void test4E1a_application() {
		doTest4E_1and2(SUBSYSTEM_4E1A_COMPOSITE_1, SUBSYSTEM_4E_APPLICATION_2, false);
	}

	// TestPlan item 4E1a composites
	public void test4E1a_composite() {
		doTest4E_1and2(SUBSYSTEM_4E1A_COMPOSITE_1, SUBSYSTEM_4E_COMPOSITE_2, false);
	}

	// TestPlan item 4E1a features
	public void test4E1a_feature() {
		doTest4E_1and2(SUBSYSTEM_4E1A_COMPOSITE_1, SUBSYSTEM_4E_FEATURE_2, false);
	}

	// TestPlan item 4E1b composite+application
	public void test4E1b_comp_app() {
		doTest4E_1and2(SUBSYSTEM_4E1B_COMPOSITE_1A, null, false);
	}

	// TestPlan item 4E1b composite+composites
	public void test4E1b_comp_comp() {
		doTest4E_1and2(SUBSYSTEM_4E1B_COMPOSITE_1C, null, false);
	}

	// TestPlan item 4E1b composite+features
	public void test4E1b_comp_feat() {
		doTest4E_1and2(SUBSYSTEM_4E1B_COMPOSITE_1F, null, false);
	}

	// TestPlan item 4E1b application+application
	public void test4E1b_app_app() {
		doTest4E_1and2(SUBSYSTEM_4E1B_APPLICATION_1A, null, false);
	}

	// TestPlan item 4E1b application+composites
	public void test4E1b_app_comp() {
		doTest4E_1and2(SUBSYSTEM_4E1B_APPLICATION_1C, null, false);
	}

	// TestPlan item 4E1b application+features
	public void test4E1b_app_feat() {
		doTest4E_1and2(SUBSYSTEM_4E1B_APPLICATION_1F, null, false);
	}

	// TestPlan item 4E2a application
	public void test4E2a_application() {
		doTest4E_1and2(SUBSYSTEM_4E2A_COMPOSITE_1, SUBSYSTEM_4E_APPLICATION_2, true);
	}

	// TestPlan item 4E2a composites
	public void test4E2a_composite() {
		doTest4E_1and2(SUBSYSTEM_4E2A_COMPOSITE_1, SUBSYSTEM_4E_COMPOSITE_2, true);
	}

	// TestPlan item 4E2a features
	public void test4E2a_feature() {
		doTest4E_1and2(SUBSYSTEM_4E2A_COMPOSITE_1, SUBSYSTEM_4E_FEATURE_2, true);
	}

	// TestPlan item 4E2b composite+application
	public void test4E2b_comp_app() {
		doTest4E_1and2(SUBSYSTEM_4E2B_COMPOSITE_1A, null, true);
	}

	// TestPlan item 4E2b composite+composites
	public void test4E2b_comp_comp() {
		doTest4E_1and2(SUBSYSTEM_4E2B_COMPOSITE_1C, null, true);
	}

	// TestPlan item 4E2b composite+features
	public void test4E2b_comp_feat() {
		doTest4E_1and2(SUBSYSTEM_4E2B_COMPOSITE_1F, null, true);
	}

	// TestPlan item 4E2b application+application
	public void test4E2b_app_app() {
		doTest4E_1and2(SUBSYSTEM_4E2B_APPLICATION_1A, null, true);
	}

	// TestPlan item 4E2b application+composites
	public void test4E2b_app_comp() {
		doTest4E_1and2(SUBSYSTEM_4E2B_APPLICATION_1C, null, true);
	}

	// TestPlan item 4E1b application+features
	public void test4E2b_app_feat() {
		doTest4E_1and2(SUBSYSTEM_4E2B_APPLICATION_1F, null, true);
	}
	
	private void doTest4E_1and2(String subsystemName1, String subsystemName2, boolean acceptDependencies) {
		registerRepository(REPOSITORY_2);
		Subsystem root = getRootSubsystem();
		Subsystem s1 = doSubsystemInstall(getName(), root, subsystemName1, subsystemName1, false);
		Subsystem s2;
		if (subsystemName2 != null) {
			s2 = doSubsystemInstall(getName(), s1, subsystemName2, subsystemName2, false);
		} else {
			s2 = s1.getChildren().iterator().next();
		}

		Collection<Resource> s2Constituents = s2.getConstituents();
		assertNotNull("Null constituents.", s2Constituents);
		if (SubsystemConstants.SUBSYSTEM_TYPE_FEATURE.equals(s2.getType())) {
			// there should be 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 3, s2Constituents.size());
		} else {
			// there should be the context + 3 (C, D, E) bundles
			assertEquals("Wrong number of constituents.", 4, s2Constituents.size());
		}

		Bundle a = getBundle(acceptDependencies ? s1 : root, BUNDLE_SHARE_A);
		Bundle b = getBundle(acceptDependencies ? s1 : root, BUNDLE_SHARE_B);
		Bundle c = getBundle(s2, BUNDLE_SHARE_C);
		Bundle d = getBundle(s2, BUNDLE_SHARE_D);
		Bundle e = getBundle(s2, BUNDLE_SHARE_E);

		doSubsystemOperation("start s1", s1, Operation.START, false);
		if (subsystemName2 != null) {
			doSubsystemOperation("start s2", s2, Operation.START, false);
		}
		for (Bundle bundle : new Bundle[] {a, b, c, d, e} ) {
			assertEquals("Wrong state for the bundle: " + bundle.getSymbolicName(), Bundle.ACTIVE, bundle.getState());
		}
		checkWiring(a, a, b, c, d, e);
	}

	// Test plan item 4E3a application
	public void test4E3a_application() {
		doTest4E3(SUBSYSTEM_EMPTY_COMPOSITE_E, SUBSYSTEM_4E_APPLICATION_2);
	}

	// Test plan item 4E3a composite
	public void test4E3a_composite() {
		doTest4E3(SUBSYSTEM_EMPTY_COMPOSITE_E, SUBSYSTEM_4E_COMPOSITE_2);
	}

	// Test plan item 4E3a feature
	public void test4E3a_feature() {
		doTest4E3(SUBSYSTEM_EMPTY_COMPOSITE_E, SUBSYSTEM_4E_FEATURE_2);
	}

	// Test plan item 4E3b application
	public void test4E3b_application() {
		doTest4E3(SUBSYSTEM_4E3B_COMPOSITE_1A, null);
	}

	// Test plan item 4E3b composite
	public void test4E3b_composite() {
		doTest4E3(SUBSYSTEM_4E3B_COMPOSITE_1C, null);
	}

	// Test plan item 4E3b feature
	public void test4E3b_feature() {
		doTest4E3(SUBSYSTEM_4E3B_COMPOSITE_1F, null);
	}
	private void doTest4E3(String subsystemName1, String subsystemName2) {
		registerRepository(REPOSITORY_2);
		Subsystem root = getRootSubsystem();
		// s1 install will fail if subsystemName2 is null
		Subsystem s1 = doSubsystemInstall(getName(), root, subsystemName1, subsystemName1, subsystemName2 == null);
		if (subsystemName2 != null) {
			// this is always expected to fail
			doSubsystemInstall(getName(), s1, subsystemName2, subsystemName2, true);
		}
		assertNoBundle(root, BUNDLE_SHARE_A);
		assertNoBundle(root, BUNDLE_SHARE_B);
	}

	// Test plan item 4F1a
	public void test4F1_application() {
		doTest4F(SUBSYSTEM_4F1_APPLICATION, null);
	}

	// Test plan item 4F1b
	public void test4F1_composite() {
		doTest4F(SUBSYSTEM_4F1_COMPOSITE, null);
	}

	// Test plan item 4F2a
	public void test4F2_app_comp() {
		doTest4F(SUBSYSTEM_4F2_PREFER_COMP_APPLICATION, SUBSYSTEM_4F2_COMPOSITE_EXPORTER);
	}

	// Test plan item 4F2b
	public void test4F2_comp_comp() {
		doTest4F(SUBSYSTEM_4F2_PREFER_COMP_COMPOSITE, SUBSYSTEM_4F2_COMPOSITE_EXPORTER);
	}

	// Test plan item 4F2c
	public void test4F2_app_feat() {
		doTest4F(SUBSYSTEM_4F2_PREFER_FEAT_APPLICATION, SUBSYSTEM_4F2_FEATURE_EXPORTER);
	}

	// Test plan item 4F2d
	public void test4F2_comp_feat() {
		doTest4F(SUBSYSTEM_4F2_PREFER_FEAT_COMPOSITE, SUBSYSTEM_4F2_FEATURE_EXPORTER);
	}

	private void doTest4F(String subsystemImporter, String subsystemExporter) {
		registerRepository(REPOSITORY_1);
		Subsystem root = getRootSubsystem();

		Subsystem provider;
		if (subsystemExporter != null) {
			// if testing preferring a subsystem make sure to pre-install A and B into root
			doBundleInstall("A in Root", root.getBundleContext(), BUNDLE_SHARE_A, BUNDLE_SHARE_A, false);
			doBundleInstall("B in Root", root.getBundleContext(), BUNDLE_SHARE_B, BUNDLE_SHARE_B, false);
			provider = doSubsystemInstall(getName(), root, subsystemExporter, subsystemExporter, false);
			doSubsystemOperation(getName(), provider, Operation.START, false);
		} else {
			provider = root;
		}

		Subsystem requirer = doSubsystemInstall(getName(), root, subsystemImporter, subsystemImporter, false);
		doSubsystemOperation(getName(), requirer, Operation.START, false);

		if (subsystemExporter == null) {
			// if not preferring a subsystem make sure A and B are not in root
			assertNoBundle(root, BUNDLE_SHARE_A);
			assertNoBundle(root, BUNDLE_SHARE_B);
		}
		Bundle c = getBundle(requirer, BUNDLE_SHARE_C);
		Bundle e = getBundle(requirer, BUNDLE_SHARE_E);
		Bundle f = getBundle(provider, BUNDLE_SHARE_F);
		Bundle g = getBundle(provider, BUNDLE_SHARE_G);
		checkWiring(f, f, g, c, null, e);
	}

	public void test4Ga() {
		registerRepository(REPOSITORY_1);
		Subsystem root = getRootSubsystem();
		doSubsystemInstall(getName(), root, getName(), SUBSYSTEM_4G1A_COMPOSITE, true);
	}
	
	public void test4H1() {
		Subsystem root = getRootSubsystem();
		Subsystem subsystem = doSubsystemInstall(getName(), root, getName(), SUBSYSTEM_4H_APPLICATION, false);
		doSubsystemOperation(getName(), subsystem, Operation.START, false);
		Bundle i = getBundle(subsystem, BUNDLE_SHARE_I);
		Bundle j = getBundle(subsystem, BUNDLE_SHARE_J);
		Bundle k = getBundle(subsystem, BUNDLE_SHARE_K);
		checkWiring(null, null, null, i, j, k);
	}
	
	public void test4H2() {
		registerRepository(REPOSITORY_2);
		Subsystem root = getRootSubsystem();
		Subsystem subsystem = doSubsystemInstall(getName(), root, getName(), SUBSYSTEM_4H_APPLICATION, false);
		doSubsystemOperation(getName(), subsystem, Operation.START, false);
		Bundle a = getBundle(root, BUNDLE_SHARE_A);
		Bundle b = getBundle(root, BUNDLE_SHARE_B);
		Bundle i = getBundle(subsystem, BUNDLE_SHARE_I);
		Bundle j = getBundle(subsystem, BUNDLE_SHARE_J);
		Bundle k = getBundle(subsystem, BUNDLE_SHARE_K);
		checkWiring(a, a, b, i, j, k);
	}

	private void checkWiring(Bundle packageExporter, Bundle bundleProvider, Bundle capabilityProvider, Bundle packageImporter, Bundle bundleRequirer, Bundle capabilityRequirer) {
		BundleWiring pExporterWiring = packageExporter == null ? null : packageExporter.adapt(BundleRevision.class).getWiring();
		BundleWiring bProviderWiring = bundleProvider == null ? null : bundleProvider.adapt(BundleRevision.class).getWiring();
		BundleWiring cProviderWiring = capabilityProvider == null ? null : capabilityProvider.adapt(BundleRevision.class).getWiring();

		if (packageImporter != null) {
			BundleWiring pImporterWiring = packageImporter.adapt(BundleRevision.class).getWiring();
			List<BundleWire> pWires = pImporterWiring.getRequiredWires(PackageNamespace.PACKAGE_NAMESPACE);
			if (pExporterWiring != null) {
				assertEquals("Wrong number of packages", 1, pWires.size());
				assertEquals("Wrong package provider.", pExporterWiring, pWires.get(0).getProviderWiring());
			}
			else {
				assertEquals("Wrong number of packages", 0, pWires.size());
			}
		}
		if (bundleRequirer != null)  {
			BundleWiring bRequirerWiring = bundleRequirer.adapt(BundleRevision.class).getWiring();
			List<BundleWire> bWires = bRequirerWiring.getRequiredWires(BundleNamespace.BUNDLE_NAMESPACE);
			if (bProviderWiring != null) {
				assertEquals("Wrong number of bundles", 1, bWires.size());
				assertEquals("Wrong bundle provider.", bProviderWiring, bWires.get(0).getProviderWiring());
			}
			else {
				assertEquals("Wrong number of packages", 0, bWires.size());
			}
		}
		if (capabilityRequirer != null) {
			BundleWiring cRequirerWiring = capabilityRequirer.adapt(BundleRevision.class).getWiring();
			List<BundleWire> cWires = cRequirerWiring.getRequiredWires(null);
			if (cProviderWiring != null) {
				assertEquals("Wrong number of capabilities", 1, cWires.size());
				assertEquals("Wrong capability provider.", cProviderWiring, cWires.get(0).getProviderWiring());
			}
			else {
				assertEquals("Wrong number of packages", 0, cWires.size());
			}
		}
	}
}