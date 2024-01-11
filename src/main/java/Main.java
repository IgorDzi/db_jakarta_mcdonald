import entity.CategoriesEntity;
import entity.ProductsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em  = emf.createEntityManager();


        // a

        var queryStringA = "SELECT ROUND(COUNT(p) * 100.0 / ((SELECT COUNT(p2) FROM ProductsEntity p2)), 2) FROM ProductsEntity p WHERE p.calcium + p.iron > 50";
        Query queryA = em.createQuery(queryStringA);
        Float resultA = (Float) queryA.getSingleResult();
        System.out.printf("Procent produktów spełniających warunek z podpunktu a: %s%%", resultA);

        // b

        var queryStringB = "SELECT AVG(calories) FROM ProductsEntity WHERE LOWER(itemName) LIKE '%bacon%'";
        Query queryB = em.createQuery(queryStringB);
        Double resultB = (Double) queryB.getSingleResult();
        System.out.printf("\nŚrednia wartość kaloryczna produktów spełniających warunek z podpunktu b: %s kcal", resultB);

        // c

        var queryStringC = "SELECT p1.category, p1.itemName , p1.cholesterole FROM ProductsEntity p1 WHERE cholesterole=(SELECT MAX(p2.cholesterole) FROM ProductsEntity p2 WHERE p2.category = p1.category)";
        Query queryC = em.createQuery(queryStringC);
        List<Object[]> resultC =  queryC.getResultList();
        System.out.println("\nRozwiązanie podpunktu c:");
        for (Object[] result : resultC) {
            CategoriesEntity category = (CategoriesEntity) result[0];
            String itemName = (String) result[1];
            Integer cholesterolValue = (Integer) result[2];
            System.out.println("Category: " + category.getCatName());
            System.out.println("Product Name: " + itemName);
            System.out.println(" Cholesterol Value:" + cholesterolValue);
            System.out.println("-----------------------------------------");}




        // d

        var queryStringD = "SELECT COUNT(p) FROM ProductsEntity p WHERE LOWER(itemName) LIKE '%mocha%' OR LOWER(itemName) LIKE'%coffee%' AND fiber=0";
        Query queryD = em.createQuery(queryStringD);
        Long resultD = (Long) queryD.getSingleResult();
        System.out.printf("\nLiczba produktów spełniających warunek z podpunktu d: %s", resultD);

        // e

        var queryStringE = "SELECT p1.itemName , p1.calories * 4184 AS Calories_Joules  FROM ProductsEntity p1 WHERE itemName LIKE '%McMuffin%'";
        Query queryE = em.createQuery(queryStringE);
        List<Object[]> resultE =  queryE.getResultList();
        System.out.println("\nRozwiązanie podpunktu e:");
        for (Object[] result : resultE) {
           String itemName = (String) result[0];
           Integer calories = (Integer) result[1];
            System.out.println("Product Name: " + itemName);
            System.out.println("Calories in Joules: " + calories);
            System.out.println("-----------------------------------------");}


        // f

        var queryStringF = "SELECT COUNT(DISTINCT carbs) FROM ProductsEntity  ";
        Query queryF = em.createQuery(queryStringF);
        Long resultF = (Long) queryF.getSingleResult();
        System.out.printf("\nLiczba różnych wartości węglowodanów: %s", resultF);

}}
