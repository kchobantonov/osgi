/*
 * Copyright (c) OSGi Alliance (2001, 2013). All Rights Reserved.
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

package java.security.cert;
public class CertPathValidatorException extends java.security.GeneralSecurityException {
	public enum BasicReason implements java.security.cert.CertPathValidatorException.Reason {
		ALGORITHM_CONSTRAINED,
		EXPIRED,
		INVALID_SIGNATURE,
		NOT_YET_VALID,
		REVOKED,
		UNDETERMINED_REVOCATION_STATUS,
		UNSPECIFIED;
	}
	public interface Reason extends java.io.Serializable {
	}
	public CertPathValidatorException() { } 
	public CertPathValidatorException(java.lang.String var0) { } 
	public CertPathValidatorException(java.lang.String var0, java.lang.Throwable var1) { } 
	public CertPathValidatorException(java.lang.String var0, java.lang.Throwable var1, java.security.cert.CertPath var2, int var3) { } 
	public CertPathValidatorException(java.lang.String var0, java.lang.Throwable var1, java.security.cert.CertPath var2, int var3, java.security.cert.CertPathValidatorException.Reason var4) { } 
	public CertPathValidatorException(java.lang.Throwable var0) { } 
	public java.security.cert.CertPath getCertPath() { return null; }
	public int getIndex() { return 0; }
	public java.security.cert.CertPathValidatorException.Reason getReason() { return null; }
}
