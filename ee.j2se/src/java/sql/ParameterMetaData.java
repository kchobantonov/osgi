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

package java.sql;
public interface ParameterMetaData extends java.sql.Wrapper {
	public final static int parameterModeIn = 1;
	public final static int parameterModeInOut = 2;
	public final static int parameterModeOut = 4;
	public final static int parameterModeUnknown = 0;
	public final static int parameterNoNulls = 0;
	public final static int parameterNullable = 1;
	public final static int parameterNullableUnknown = 2;
	java.lang.String getParameterClassName(int var0) throws java.sql.SQLException;
	int getParameterCount() throws java.sql.SQLException;
	int getParameterMode(int var0) throws java.sql.SQLException;
	int getParameterType(int var0) throws java.sql.SQLException;
	java.lang.String getParameterTypeName(int var0) throws java.sql.SQLException;
	int getPrecision(int var0) throws java.sql.SQLException;
	int getScale(int var0) throws java.sql.SQLException;
	int isNullable(int var0) throws java.sql.SQLException;
	boolean isSigned(int var0) throws java.sql.SQLException;
}
