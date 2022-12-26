module com.example.gsbrvdr {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;

    opens com.example.gsbrvdr to javafx.fxml;
    exports com.example.gsbrvdr;
    exports fr.gsb.rv.dr;
}