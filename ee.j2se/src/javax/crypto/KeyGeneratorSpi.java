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

package javax.crypto;
public abstract class KeyGeneratorSpi {
	public KeyGeneratorSpi() { } 
	protected abstract javax.crypto.SecretKey engineGenerateKey();
	protected abstract void engineInit(int var0, java.security.SecureRandom var1);
	protected abstract void engineInit(java.security.SecureRandom var0);
	protected abstract void engineInit(java.security.spec.AlgorithmParameterSpec var0, java.security.SecureRandom var1) throws java.security.InvalidAlgorithmParameterException;
}
