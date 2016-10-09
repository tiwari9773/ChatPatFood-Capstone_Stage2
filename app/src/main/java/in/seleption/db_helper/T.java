package in.seleption.db_helper;

/**
 * Created by Lokesh on 23-09-2015.
 */
public interface T {

    /*** Defining Every DML , DDL Key word as Constant so that Typing mistake
     * should be completely removed
     * 1) It also helps to AutoComplete the statement
     * 2) Manage spaces between ColumnName and Type
     * this is how I prevent comma, parenthesis mistakes*/

    /* Data Type And Separator */
    String TYPE_INTEGER = " INTEGER ";
    String TYPE_BOOLEAN = " BOOLEAN ";
    String TYPE_TEXT = " TEXT ";
    String TYPE_REAL = " REAL ";
    String SEP_COMMA = " , ";

    String CLOSE_BRACE = " ) ";
    String OPEN_BRACE = " ( ";
    String SEMICOLON = " ; ";

    String DROP_TABLE = "DROP TABLE IF EXISTS ";
    String AUTO_INCREMENT = " AUTOINCREMENT ";
    String CREATE_TABLE = " CREATE TABLE ";

    /* Primary Constraints of Table*/
    String PRIMARY_KEY = " PRIMARY KEY ";
    String FOREIGN_KEY = " FOREIGN KEY ";
    String REFERENCES = " REFERENCES ";
    String NOT_NULL = " NOT NULL ";

    /* Constraints */
    String ON_CONFLICT_REPLACE = " ON CONFLICT REPLACE ";
    String ON_CONFLICT_IGNORE = " ON CONFLICT IGNORE ";
    String UNIQUE = " UNIQUE ";

}
