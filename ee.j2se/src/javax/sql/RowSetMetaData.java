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

package javax.sql;
public interface RowSetMetaData extends java.sql.ResultSetMetaData {
	void setAutoIncrement(int var0, boolean var1) throws java.sql.SQLException;
	void setCaseSensitive(int var0, boolean var1) throws java.sql.SQLException;
	void setCatalogName(int var0, java.lang.String var1) throws java.sql.SQLException;
	void setColumnCount(int var0) throws java.sql.SQLException;
	void setColumnDisplaySize(int var0, int var1) throws java.sql.SQLException;
	void setColumnLabel(int var0, java.lang.String var1) throws java.sql.SQLException;
	void setColumnName(int var0, java.lang.String var1) throws java.sql.SQLException;
	void setColumnType(int var0, int var1) throws java.sql.SQLException;
	void setColumnTypeName(int var0, java.lang.String var1) throws java.sql.SQLException;
	void setCurrency(int var0, boolean var1) throws java.sql.SQLException;
	void setNullable(int var0, int var1) throws java.sql.SQLException;
	void setPrecision(int var0, int var1) throws java.sql.SQLException;
	void setScale(int var0, int var1) throws java.sql.SQLException;
	void setSchemaName(int var0, java.lang.String var1) throws java.sql.SQLException;
	void setSearchable(int var0, boolean var1) throws java.sql.SQLException;
	void setSigned(int var0, boolean var1) throws java.sql.SQLException;
	void setTableName(int var0, java.lang.String var1) throws java.sql.SQLException;
}
