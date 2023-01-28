module com.example.gsbrvdr {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires org.mariadb.jdbc;
    requires jdk.scripting.nashorn;

    opens com.example.gsbrvdr to javafx.fxml, javafx.base;
    opens fr.gsb.rv.dr.entites to javafx.base;
    exports fr.gsb.rv.dr;
}