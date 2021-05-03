import Client.*;
import Common.*;
import Server.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GUI GUI = new GUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
