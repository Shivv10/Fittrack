package comp3350.fittrack.persistence.hsqldb.exceptions;

import java.sql.SQLException;

public class HSQLDBException extends RuntimeException
{
    public HSQLDBException(SQLException cause) { super("Database error: " + cause.getMessage(), cause); }
}

