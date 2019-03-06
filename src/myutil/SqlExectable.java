package myutil;

import java.sql.Connection;

@FunctionalInterface
public interface SqlExectable {

    Object exec(Connection con);

}
