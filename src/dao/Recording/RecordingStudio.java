package dao.Recording;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import java.sql.*;

import Exceptions.VariableIsNull;
import Exceptions.StatementDontReturnValueException;
import Exceptions.ZeroRowChangedException;
import Model.Compositions.Composition;


public class RecordingStudio {


    public static Connection getConnection() {
        return connection;
    }

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

    public List<Composition> getCompositionsByDuration(double d1, double d2) throws StatementDontReturnValueException {
        List<Composition> compositions;
        List<String> genreNames;
        List<Integer> genresId;
//        try {
            compositions = new LinkedList<>();
            genreNames = new LinkedList<>();

//            PreparedStatement preparedCompositionsStatement = connection.prepareStatement(
//                    "SELECT * FROM Assemblage.Compositions WHERE duration BETWEEN (?, ?)");
//            preparedCompositionsStatement.setDouble(1, d1);
//            preparedCompositionsStatement.setDouble(2, d2);
//            ResultSet resultCompositionsSet = preparedCompositionsStatement.executeQuery();
            compositions = selectCompositionsByDuration(d1,d2);


//            while(resultCompositionsSet.next()) {
            for (Composition composition : compositions) {
//                PreparedStatement preparedGenreOfCompositionsStatement = connection.prepareStatement(
//                        "SELECT * FROM Assemblage.GenreOfCompositions WHERE compositionID = ?");
//                preparedGenreOfCompositionsStatement.setInt(1,
//                        resultCompositionsSet.getInt("compositionID"));
//                ResultSet resultGenreIDSet = preparedGenreOfCompositionsStatement.executeQuery();
                genresId = getGenresIdByCompositionId(composition.getId());

//                while(resultGenreIDSet.next()){
                for (int genreId : genresId) {
//                    PreparedStatement preparedGenreStatement = connection.prepareStatement(
//                            "SELECT * FROM Assemblage.Genre WHERE genreID = ?");
//                    preparedGenreStatement.setString(1,
//                            String.valueOf(resultGenreIDSet.getInt("genreID")));
//                    ResultSet resultGenreSet = preparedGenreStatement.executeQuery();
//                    if(!resultGenreSet.next())
//                        break;
//                    genreNames.add(resultGenreSet.getString("genreNames"));
                    genreNames.add(getGenreNameById(genreId));

//                    preparedGenreStatement.close();
//                    resultGenreSet.close();
                }

//                PreparedStatement preparedAssemblageNamesStatement = connection.prepareStatement(
//                        "SELECT * FROM Assemblage.AssemblageNames WHERE assemblageID = ?"); // Перевірити, чи assemblageName може повторюватися.
//                preparedAssemblageNamesStatement.setInt(1,
//                        resultCompositionsSet.getInt("assemblageID"));
//                ResultSet resultAssemblageNamesSet = preparedAssemblageNamesStatement.executeQuery();
//                if(!resultAssemblageNamesSet.next())
//                    return null;
                selectAssemblageNameById(Integer.parseInt(composition.getAssemblageName()));

//                Composition composition = new Composition();
//                composition.setId(resultCompositionsSet.getInt("compositionID"));
//                composition.setName(resultCompositionsSet.getString("name"));
//                composition.setPerformer(resultCompositionsSet.getString("performer"));
//                composition.setDuration(resultCompositionsSet.getFloat("duration"));
//                composition.setAssemblageName(resultAssemblageNamesSet.getString("assemblageName"));
                composition.setGenres(genreNames);

//                compositions.add(composition);
//                preparedGenreOfCompositionsStatement.close();
//                preparedAssemblageNamesStatement.close();
//                resultGenreIDSet.close();
//                resultAssemblageNamesSet.close();
            }


//            preparedCompositionsStatement.close();
//            resultCompositionsSet.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return compositions;
    }

// ----------------------------<Розбиття функції getCompositionsByDuration на менші>----------------------------

    public List<Composition> selectCompositionsByDuration(double d1, double d2) throws StatementDontReturnValueException {
        List<Composition> compositions;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            compositions = new LinkedList<>();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.Compositions WHERE duration BETWEEN ? AND ?");
            preparedStatement.setDouble(1, d1);
            preparedStatement.setDouble(2, d2);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new StatementDontReturnValueException("Збірки за даним ID не існує в базі даних!");
            do {
                Composition composition = new Composition();
                composition.setId(resultSet.getInt("compositionID"));
                composition.setName(resultSet.getString("name"));
                composition.setPerformer(resultSet.getString("performer"));
                composition.setDuration(resultSet.getFloat("duration"));
//                composition.setAssemblageName(assemblageName);
                composition.setAssemblageName(resultSet.getString("assemblageID")); ///////////////
                compositions.add(composition);
            } while (resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return compositions;
    }

// --------------------------------------------------< >--------------------------------------------------

    public void insertComposition(Composition composition) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException { // Вставляє композицію в уже створену збірку.
        List<String> genres;
        int assemblageId = getAssemblageIdByName(composition.getAssemblageName());

        insertIntoComposition(composition, assemblageId);
        increaseTotalDurationOfAssemblage((float) composition.getDuration(), assemblageId);

        int currentCompositionId = getCurrentCompositionId();

        genres = composition.getGenres();
        int genreId;
        for (String genre : genres) {
            try {
                genreId = getGenreIdByName(genre);
            } catch (StatementDontReturnValueException e) {
                insertIntoGenre(genre);
                genreId = getCurrentGenreId();
            }

            insertIntoGenreOfComposition(currentCompositionId, genreId);
        }

    }

    // ----------------------------<Розбиття функції insertComposition на менші>----------------------------
    public int getAssemblageIdByName(String assemblageName) throws VariableIsNull, StatementDontReturnValueException {
        int assemblageID;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if(assemblageName == null)
            throw new VariableIsNull("Змінна з назвою збірки є пустою!");
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT assemblageID FROM Assemblage.AssemblageNames WHERE assemblageName = ?");
            preparedStatement.setString(1, assemblageName);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new StatementDontReturnValueException("Збірки з даним іменем не існує!");

            assemblageID = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return assemblageID;
    }

    public void insertIntoComposition(Composition composition, int assemblageId) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.Compositions (name, duration, assemblageID, performer) VALUES (?,?,?,?)");
            preparedStatement.setString(1, composition.getName());
            preparedStatement.setFloat(2, (float) composition.getDuration());
            preparedStatement.setInt(3, assemblageId);
            preparedStatement.setString(4, composition.getPerformer());
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти композицію!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }
    public void increaseTotalDurationOfAssemblage(float compositionDuration, int assemblageId) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE Assemblage.AssemblageNames SET totalDuration = totalDuration + ? WHERE assemblageId = ?");
            preparedStatement.setFloat(1, (float) compositionDuration);
            preparedStatement.setInt(2, assemblageId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти назву збірки!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }
    public int getCurrentCompositionId() throws StatementDontReturnValueException {
        int compositionId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(compositionID) FROM Assemblage.Compositions");
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new  StatementDontReturnValueException("Композиції відсутні в базі даних!");
            compositionId = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return compositionId;
    }
    public int getCurrentGenreId() throws StatementDontReturnValueException {
        int genreId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(genreID) FROM Assemblage.Genre");
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new StatementDontReturnValueException("Жанри відсутні в базі даних!");
            genreId = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert preparedStatement != null && resultSet != null;
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return genreId;
    }
    public int getGenreIdByName(String genre) throws StatementDontReturnValueException{
        int genreId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT genreID FROM Assemblage.Genre WHERE genreName LIKE ?");
            preparedStatement.setString(1, genre);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new StatementDontReturnValueException("Жанрів з таким іменем не існує в базі даних!");
            genreId = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return genreId;
    }

    public void insertIntoGenre(String genreName) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.Genre (genreName) VALUES(?)");
            preparedStatement.setString(1, genreName);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти назву жанру!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertIntoGenreOfComposition(int compositionId, int genreId) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.GenreOfCompositions (compositionID, genreID) VALUES (?,?)");
            preparedStatement.setInt(1, compositionId);
            preparedStatement.setInt(2, genreId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти жанри композиції!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

// --------------------------------------------------< >--------------------------------------------------
    public List<Composition> getAssemblage(String assemblageName) throws StatementDontReturnValueException, VariableIsNull { // Виводить усі композиції за збіркою.
        List<Composition> assemblage = new LinkedList<>();
        List<String> genreNames = new LinkedList<>();

        int assemblageId = getAssemblageIdByName(assemblageName);
        List<Composition> compositions = getCompositionsByAssemblageId(assemblageId);
        for (Composition composition : compositions) {
            List<Integer> genresId = getGenresIdByCompositionId(composition.getId());
            for (Integer genreId : genresId) {
                String genreName = getGenreNameById(genreId);
                genreNames.add(genreName);
            }
            composition.setAssemblageName(assemblageName);
            composition.setGenres(genreNames);

            assemblage.add(composition);
        }
        return assemblage;
    }

//    public void test3(int id, Composition composition) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "UPDATE Compositions SET name = ?, genre = ?, duration = ?, assemblageName = ? " +
//                            "WHERE id = ?");
//            preparedStatement.setString(1, composition.getName());
////            preparedStatement.setString(2, composition.getGenre());
//            preparedStatement.setFloat(3, (float) composition.getDuration());
//            preparedStatement.setString(4, composition.getAssemblageName());
//            preparedStatement.setInt(5, id);
//
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    // ----------------------------<Розбиття функції getAssemblage на менші>----------------------------
    public List<Composition> getCompositionsByAssemblageId(int assemblageId) throws StatementDontReturnValueException {
        List<Composition> compositions;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            compositions = new LinkedList<>();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.Compositions WHERE assemblageID = ?");
            preparedStatement.setInt(1, assemblageId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new StatementDontReturnValueException("Збірки за даним ID не існує в базі даних!");
            do {
                Composition composition = new Composition();
                composition.setId(resultSet.getInt("compositionID"));
                composition.setName(resultSet.getString("name"));
                composition.setPerformer(resultSet.getString("performer"));
                composition.setDuration(resultSet.getFloat("duration"));
//                composition.setAssemblageName(assemblageName);
                compositions.add(composition);
            } while (resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return compositions;
    }

    public List<Integer> getGenresIdByCompositionId(int compositionId) throws StatementDontReturnValueException {
        List<Integer> genresId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            genresId = new LinkedList<>();
            preparedStatement = connection.prepareStatement(
                    "SELECT genreId FROM Assemblage.GenreOfCompositions WHERE compositionID = ?");
            preparedStatement.setInt(1, compositionId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new StatementDontReturnValueException("Жанрів для композиції з таким ID не існує в базі даних!");
            do {
                genresId.add(resultSet.getInt("genreId"));
            } while (resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return genresId;
    }

    public String getGenreNameById(int genreId) throws StatementDontReturnValueException {
        String genreName;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT genreName FROM Assemblage.Genre WHERE genreID = ?");
            preparedStatement.setInt(1, genreId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new StatementDontReturnValueException("Жанрів з таким ID не існує в базі даних!");
            genreName = resultSet.getString("genreName");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return genreName;
    }


    public List<String> getAssemblageNames() throws StatementDontReturnValueException {
        List<String> assemblageNames =  null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT assemblageName FROM Assemblage.AssemblageNames");
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new StatementDontReturnValueException("Назви збірок відсутні в базі даних!");

            assemblageNames = new LinkedList<>();
            do {
                assemblageNames.add(resultSet.getString(1));
            } while(resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return assemblageNames;
    }

// --------------------------------------------------< >--------------------------------------------------
    public void deleteAssemblage(String assemblageName) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
//        try {
//            PreparedStatement preparedAssemblageNamesStatement = connection.prepareStatement(
//                    "SELECT assemblageID FROM Assemblage.AssemblageNames WHERE assemblageName LIKE ?");
//            preparedAssemblageNamesStatement.setString(1, assemblageName);
//            ResultSet resultSet = preparedAssemblageNamesStatement.executeQuery();
//            if(!resultSet.next())
//                return;
//            int assemblageID = resultSet.getInt(1);
            int assemblageId = getAssemblageIdByName(assemblageName);


//            PreparedStatement preparedDeleteCompositionsStatement = connection.prepareStatement(
//                    "DELETE FROM Assemblage.Compositions WHERE assemblageID = ?");
//            preparedDeleteCompositionsStatement.setInt(1, assemblageID);
//            preparedDeleteCompositionsStatement.executeUpdate();
            deleteCompositionsByAssemblageId(assemblageId);


//            PreparedStatement preparedDeleteAssemblageStatement = connection.prepareStatement(
//                    "DELETE FROM Assemblage.AssemblageNames WHERE assemblageID = ?");
//            preparedDeleteAssemblageStatement.setInt(1, assemblageID);
//            preparedDeleteAssemblageStatement.executeUpdate();
            deleteAssemblageNameById(assemblageId);

//            resultSet.close();
//            preparedAssemblageNamesStatement.close();
//            preparedDeleteCompositionsStatement.close();
//            preparedDeleteAssemblageStatement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    } // Додати видалення жанрів та жанрів видалених композицій.

    // ----------------------------<Розбиття функції deleteAssemblage на менші>----------------------------

    public void deleteCompositionsByAssemblageId(int assemblageID) throws ZeroRowChangedException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.Compositions WHERE assemblageID = ?")) {
            preparedStatement.setInt(1, assemblageID);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити композиції за вказаним ID збірки!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAssemblageNameById(int assemblageId) throws ZeroRowChangedException {
        try (PreparedStatement preparedDeleteAssemblageStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.AssemblageNames WHERE assemblageID = ?")){
            preparedDeleteAssemblageStatement.setInt(1, assemblageId);
            if(preparedDeleteAssemblageStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити збірку за вказаним ID!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --------------------------------------------------< >--------------------------------------------------

    public Composition getCompositionById(int compositionID) throws StatementDontReturnValueException {
        List<String> genreNames =  null;
        List<Integer> genresId =  null;
        Composition composition = null;
        String assemblageName = null;
//        try {
            genreNames = new LinkedList<>();
//            PreparedStatement preparedGenreOfCompositionsStatement = connection.prepareStatement(
//                    "SELECT * FROM Assemblage.GenreOfCompositions WHERE compositionID = ?");
//            preparedGenreOfCompositionsStatement.setInt(1, compositionID);
//            ResultSet resultGenreIDSet = preparedGenreOfCompositionsStatement.executeQuery();
            genresId = getGenresIdByCompositionId(compositionID);

//            while(resultGenreIDSet.next()){
            for (int genreId : genresId) {
//                PreparedStatement preparedGenreStatement = connection.prepareStatement(
//                        "SELECT * FROM Assemblage.Genre WHERE genreID = ?");
//                preparedGenreStatement.setInt(1,
//                        resultGenreIDSet.getInt("genreID"));
//                ResultSet resultGenreSet = preparedGenreStatement.executeQuery();
//                if(!resultGenreSet.next())
//                    break;
//                genreName.add(resultGenreSet.getString("genreName"));
                genreNames.add(getGenreNameById(genreId));


//                preparedGenreStatement.close();
//                resultGenreSet.close();
            }
//            PreparedStatement preparedCompositionsStatement = connection.prepareStatement(
//                    "SELECT * FROM Assemblage.Compositions WHERE compositionID = ?");
//            preparedCompositionsStatement.setInt(1, compositionID);
//            ResultSet resultCompositionsSet = preparedGenreOfCompositionsStatement.executeQuery();
            composition = selectCompositionById(compositionID);


//            PreparedStatement preparedAssemblageNamesStatement = connection.prepareStatement(
//                    "SELECT * FROM Assemblage.AssemblageNames WHERE assemblageID = ?");
//            preparedAssemblageNamesStatement.setInt(1,
//                    resultCompositionsSet.getInt("assemblageID"));
//            ResultSet resultAssemblageNamesSet = preparedAssemblageNamesStatement.executeQuery();
            assemblageName = selectAssemblageNameById(Integer.parseInt(composition.getAssemblageName())); ////


//            composition.setId(resultCompositionsSet.getInt("compositionID"));
//            composition.setName(resultCompositionsSet.getString("name"));
//            composition.setPerformer(resultCompositionsSet.getString("performer"));
//            composition.setDuration(resultCompositionsSet.getFloat("duration"));
//            composition.setAssemblageName(resultAssemblageNamesSet.getString("assemblageName"));
            composition.setAssemblageName(assemblageName);
            composition.setGenres(genreNames);

//            preparedGenreOfCompositionsStatement.close();
//            resultGenreIDSet.close();
//            preparedCompositionsStatement.close();
//            resultCompositionsSet.close();
//            preparedAssemblageNamesStatement.close();
//            resultAssemblageNamesSet.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return composition;
    }

    // ----------------------------<Розбиття функції getCompositionByID на менші>----------------------------

    public Composition selectCompositionById(int compositionID) throws StatementDontReturnValueException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Composition composition = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Assemblage.Compositions WHERE compositionID = ?");
            preparedStatement.setInt(1, compositionID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new StatementDontReturnValueException("Композиції за даним ID не існує в базі даних!");

            composition = new Composition();
            composition.setId(resultSet.getInt("compositionID"));
            composition.setName(resultSet.getString("name"));
            composition.setPerformer(resultSet.getString("performer"));
            composition.setDuration(resultSet.getFloat("duration"));
            composition.setAssemblageName(String.valueOf(resultSet.getInt("assemblageID")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return composition;
    }

    public String selectAssemblageNameById(int assemblageId) throws StatementDontReturnValueException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String assemblageName = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT assemblageName FROM Assemblage.AssemblageNames WHERE assemblageID = ?");
            preparedStatement.setInt(1, assemblageId);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new StatementDontReturnValueException("Назви збірки за даним ID не існує в базі даних!");

            assemblageName = resultSet.getString("assemblageName");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return assemblageName;
    }

// --------------------------------------------------< >--------------------------------------------------

    public void deleteCompositionById(int compositionId) throws ZeroRowChangedException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.Compositions WHERE compositionID = ?")) {
            preparedStatement.setInt(1, compositionId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити композиції за вказаним ID!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// --------------------------------------------------< >--------------------------------------------------

//    public void updateCompositionById(int compositionId, Composition composition) throws ZeroRowChangedException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(
//                "UPDATE Assemblage.Compositions SET  WHERE compositionID = ?")) {
//            preparedStatement.setInt(1, compositionId);
//            if(preparedStatement.executeUpdate() == 0)
//                throw new ZeroRowChangedException("Не вдалося видалити композиції за вказаним ID!");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

// --------------------------------------------------< >--------------------------------------------------

    public List<Integer> selectCompositionsIdByGenreId(int genreId) throws StatementDontReturnValueException {
        List<Integer> compositionsId = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            compositionsId = new LinkedList<>();
            preparedStatement = connection.prepareStatement(
                    "SELECT compositionID FROM Assemblage.GenreOfCompositions WHERE genreID = ?");
            preparedStatement.setInt(1, genreId);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new StatementDontReturnValueException("Комозицій з такими жанрами не існує в базі даних!");
            do {
                compositionsId.add(resultSet.getInt("compositionID"));
            } while (resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return compositionsId;
    }
    public List<Composition> getCompositionsByGenreName(String genreName) throws StatementDontReturnValueException {
        int genreId;
        List<Integer> compositionsId = null;
        List<Composition> compositions = new LinkedList<>();

        genreId = getGenreIdByName(genreName);
        compositionsId = selectCompositionsIdByGenreId(genreId);
        for (int compositionId : compositionsId)
            compositions.add(getCompositionById(compositionId));
        return compositions;
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
