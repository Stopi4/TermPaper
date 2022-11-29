package dao.Recording;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.sql.*;

import Model.Compositions.Composition;

public class RecordingStudio {


//    private static Composition composition;
//    private static HashMap<String, LinkedList<Composition>> collections;

    private List<Composition> compositions;
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "1234";
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

//    private static final String URL = "jdbc:sqlserver://DESKTOP-3096NSM\\SQLEXPRESS"; // DESKTOP-3096NSM\SQLEXPRESS
//    private static final String URL = "jdbc:sqlserver://DESKTOP-3096NSM\\stepa;"
    private static final String URL = "jdbc:sqlserver://DESKTOP-3096NSM:1433;"
                                      + "databaseName=TermPaper;"+"integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "stepa";
    private static final String PASSWORD = "";

    private static Connection connection;
    static {
        try {
//            Class.forName("org.postgresql.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection = DriverManager.getConnection (URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void test0 () {
        LinkedList<String> assemblage = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.Compositions");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Composition composition = new Composition();
                assemblage.add(String.valueOf(resultSet.getInt("CompositionID")));
                assemblage.add(resultSet.getString("name"));
                assemblage.add(String.valueOf(resultSet.getFloat("duration")));
                assemblage.add(String.valueOf(resultSet.getInt("assemblageID")));
                assemblage.add(resultSet.getString("performer"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(assemblage.toString());
    }



    public void test1(Composition composition) {
        LinkedList<String> genres = new LinkedList<>();
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "SELECT MAX(id) FROM Assemblage.Compositions");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(!resultSet.next())
//                return;
//            composition.setId(resultSet.getInt(1)+1);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT assemblageID FROM Assemblage.AssemblageNames WHERE assemblageName = ?");
            preparedStatement.setString(1, composition.getAssemblageName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                return;
            int assemblageID = resultSet.getInt(1);


            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.Compositions (name, duration, assemblageID, performer) VALUES (?,?,?,?,?)");
//            preparedStatement.setInt(1, composition.getId());
            preparedStatement.setString(1, composition.getName());
//            preparedStatement.setString(3, composition.getGenre());
            preparedStatement.setFloat(2, (float) composition.getDuration());
            preparedStatement.setInt(3, assemblageID);
            preparedStatement.setString(4, composition.getPerformer());


            genres = composition.getGenres();
            for (String genre : genres){
                preparedStatement = connection.prepareStatement(
                        "SELECT genreID FROM Assemblage.Genre WHERE genreName LIKE ?");
                preparedStatement.setString(1, genre);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next())
                    return;
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO Assemblage.GenreOfCompositions (compositionID, genreID) VALUES (?,?)");
                preparedStatement.setInt(1, composition.getId());
                preparedStatement.setInt(2, resultSet.getInt(1));
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<Composition> test2(String assemblageName) {
        List<Composition> assemblage;
        List<String> genreName;
        try {
            assemblage = new LinkedList<>();
            genreName = new LinkedList<>();

            PreparedStatement preparedAssemblageNamesStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.AssemblageNames WHERE assemblageName LIKE ?"); // Перевірити, чи assemblageName може повторюватися.
            preparedAssemblageNamesStatement.setString(1, assemblageName);

            //////
            PreparedStatement preparedCompositionsStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.Compositions WHERE assemblageID = ?");
            preparedCompositionsStatement.setString(1,
                    String.valueOf(preparedAssemblageNamesStatement.executeQuery().getInt("assemblageID")));
            ResultSet resultCompositionsSet = preparedCompositionsStatement.executeQuery();

            while(resultCompositionsSet.next()) {
                PreparedStatement preparedGenreStatement = connection.prepareStatement(
                        "SELECT * FROM Assemblage.GenreOfCompositions WHERE compositionID = ?");
                preparedCompositionsStatement.setString(1,
                        String.valueOf(resultCompositionsSet.getInt("compositionID")));
                ResultSet resultGenreIDSet = preparedCompositionsStatement.executeQuery();
                while(resultGenreIDSet.next()){
                    PreparedStatement preparedGenre = connection.prepareStatement(
                            "SELECT * FROM Assemblage.Genre WHERE genreID = ?");
                    preparedCompositionsStatement.setString(1,
                            String.valueOf(resultGenreIDSet.getInt("genreID")));
                    ResultSet resultGenreSet = preparedCompositionsStatement.executeQuery();
                    if(!resultGenreSet.next())
                        break;
                    genreName.add(resultGenreSet.getString("genreName"));

                    preparedGenre.close();
                    resultGenreSet.close();
                }

                Composition composition = new Composition();
                composition.setId(resultCompositionsSet.getInt("compositionID"));
                composition.setName(resultCompositionsSet.getString("name"));
                composition.setPerformer(resultCompositionsSet.getString("performer"));
                composition.setDuration(resultCompositionsSet.getFloat("duration"));

                composition.setAssemblageName(assemblageName);
                assemblage.add(composition);
                preparedGenreStatement.close();
                resultGenreIDSet.close();
            }

            preparedAssemblageNamesStatement.close();
            preparedCompositionsStatement.close();
            resultCompositionsSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assemblage;
    }

    public void test3(int id, Composition composition) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Compositions SET name = ?, genre = ?, duration = ?, assemblageName = ? " +
                            "WHERE id = ?");
            preparedStatement.setString(1, composition.getName());
            preparedStatement.setString(2, composition.getGenre());
            preparedStatement.setFloat(3, (float) composition.getDuration());
            preparedStatement.setString(4, composition.getAssemblageName());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void test4(String assemblageName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Compositions WHERE assemblageName = ?");
            preparedStatement.setString(1, assemblageName);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<String> test5() {
        List<String> assemblageNames =  null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT DISTINCT assemblageName FROM Compositions");
            ResultSet resultSet = preparedStatement.executeQuery();

            assemblageNames = new LinkedList<>();
            while(resultSet.next()) {
                assemblageNames.add(resultSet.getString(1));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assemblageNames;
    }


//    public List<String> test6(int id) {
    public Composition test6(int id) {
//        List<String> composition =  null;
        Composition composition = new Composition();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Compositions WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

//            assemblageNames = new LinkedList<>();
            if(resultSet == null)
                return null;
            while(resultSet.next()) {
//                Composition composition = new Composition();
                composition.setId(resultSet.getInt("id"));
                composition.setName(resultSet.getString("name"));
                composition.setGenre(resultSet.getString("genre"));
                composition.setDuration(resultSet.getFloat("duration"));
                composition.setAssemblageName("assemblageName");
//                assemblage.add(composition);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return composition;
    }




    public static LinkedList<Composition> createCollectionOfMusic(String nameOfCollection) {
//        if(isCollectionExist(collectionName))
        LinkedList<Composition> collectionOfComposition = new LinkedList<>();

//        collections.put(nameOfCollection, collectionOfMusic);
        return collectionOfComposition;
    }
//    public static void addCollectionToDrive(LinkedList<Music> collection) {
//        HashMap
//    }
    public static void addMusicToCollectionByCollectionName(Composition composition, String collectionName) {

    }
    public static void sortCollectionByGenre(String collectionName){

    }
    public static Composition musicByGenre(String genreName) {
        return null;
    }



    public static boolean isIdExist(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Composition WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet != null;
//            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isCollectionExist(String assemblageName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Composition WHERE assemblageName = ?");
            preparedStatement.setString(1, assemblageName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet != null;
//            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
