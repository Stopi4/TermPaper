package com.University.TempPaper.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;
import java.util.logging.FileHandler;


//import org.apache.logging.log4j.Logger;


import org.apache.logging.log4j.LogManager;
//import org.apache.log4j.Logger;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.Exceptions.*;
import org.apache.logging.log4j.Logger;


public class RecordingStudio {

//    public static final Logger LOG = LoggerFactory .getLogManager().getLogger(String.valueOf(RecordingStudio.class));
//    public static final Logger LOG = LogManager.getLogManager().getLogger(String.valueOf(RecordingStudio.class));
    private static final Logger LOG = LogManager.getLogger(RecordingStudio.class); // LogManager.getLogManager().getLogger(RecordingStudio.class.getName());
    private static FileHandler fileHandler;
    private static final String URL =// "jdbc:sqlserver://DESKTOP-3096NSM:1433;"
             "databaseName=TermPaper;"+"integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "stepa";
    private static final String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }
    static {
        initialize();
    }

    public static void initialize() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection (URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
//        try {
//            LOG.setUseParentHandlers(false);
//            fileHandler = new FileHandler("fileLog.log");
//            LOG.addHandler(fileHandler);
//            SimpleFormatter formatter = new SimpleFormatter();
//            fileHandler.setFormatter(formatter);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        LOG.setLevel(Level.WARNING);
//        LOG.info("Program connected to DB");
//        LOG.warning("Program connected to DB");

//        Configurator.initialize(new DefaultConfiguration());
//        Configurator.setRootLevel(Level.INFO);

//        PropertyConfigurator .configure("log4j.properties");
//         PropertiesConfiguration. .configure("log4j.properties");
//        Configurator.reconfigure("log4j.properties");
//        XmlConfiguration xmlConfiguration = new XmlConfiguration()


        LOG.info("Program connected to DB");
//        LOG.warn("Program connected to DB");
//        LOG.error("Program connected to DB");


    }

    public void deleteGenreOfCompositionByGenre(int compositionId, int genreId) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Assemblage.GenreOfCompositions " +
                            "WHERE compositionID = ? AND genreID = ?");
            preparedStatement.setInt(1, compositionId);
            preparedStatement.setInt(2, genreId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Жанру для цієї композиції не існує!");

        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(e);
            throw new ZeroRowChangedException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void deleteGenreOfComposition(int compositionId) throws ZeroRowChangedException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Assemblage.GenreOfCompositions " +
                            "WHERE compositionID = ?");
            preparedStatement.setInt(1, compositionId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Жанру для цієї композиції не існує!");
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
//            LOG.er
            throw new ZeroRowChangedException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void insertGenre(String genreName) throws ZeroRowChangedException, VariableIsNull {
        PreparedStatement preparedStatement = null;

        try {
            if(genreName == null)
                throw new VariableIsNull();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.Genre (genreName) VALUES (?)");
            preparedStatement.setString(1, genreName);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти жанр!");
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } catch (VariableIsNull e) {
            LOG.warn(String.valueOf(e));
            throw new VariableIsNull(e.getMessage());
        }  finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }

    public void insertAssemblageName(String assemblageName) throws VariableIsNull, ZeroRowChangedException {
        PreparedStatement preparedStatement = null;

        try {
            if(assemblageName == null)
                throw new VariableIsNull();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Assemblage.AssemblageNames (assemblageName) VALUES (?)");
            preparedStatement.setString(1, assemblageName);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося зберегти жанр!");
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } catch (VariableIsNull e) {
            LOG.warn(String.valueOf(e));
            throw new VariableIsNull(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<String> selectGenreName() throws StatementDontReturnValueException {
        List<String> genreNames =  null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT genreName FROM Assemblage.Genre");
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new StatementDontReturnValueException("Назви жанрів відсутні в базі даних!");

            genreNames = new LinkedList<>();
            do {
                genreNames.add(resultSet.getString(1));
            } while(resultSet.next());
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        }  finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return genreNames;
    }

    public List<Composition> getCompositionsByDuration(double d1, double d2) throws StatementDontReturnValueException {
        List<Composition> compositions;
        List<String> genreNames;
        List<Integer> genresId;
        compositions = new LinkedList<>();
        genreNames = new LinkedList<>();

        compositions = selectCompositionsByDuration(d1,d2);
        for (Composition composition : compositions) {
            genresId = selectGenresIdByCompositionId(composition.getId());
            for (int genreId : genresId) {
                genreNames.add(selectGenreNameById(genreId));
            }
            selectAssemblageNameById(Integer.parseInt(composition.getAssemblageName()));
            composition.setGenres(genreNames);
        }
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
                throw new StatementDontReturnValueException(
                        "Композицій з тривалістю в заданому діапазоні не існує в базі даних!");
            do {
                Composition composition = new Composition();
                composition.setId(resultSet.getInt("compositionID"));
                composition.setName(resultSet.getString("name"));
                composition.setPerformer(resultSet.getString("performer"));
                composition.setDuration(resultSet.getFloat("duration"));
                composition.setAssemblageName(resultSet.getString("assemblageID")); ///////////////
                compositions.add(composition);
            } while (resultSet.next());
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {
            }
        }
        return compositions;
    }

// --------------------------------------------------< >--------------------------------------------------

    public void insertComposition(Composition composition) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException { // Вставляє композицію в уже створену збірку.
        List<String> genres;
        int assemblageId = selectAssemblageIdByName(composition.getAssemblageName());

        insertIntoComposition(composition, assemblageId);
        increaseTotalDurationOfAssemblage((float) composition.getDuration(), assemblageId);

        int currentCompositionId = selectCurrentCompositionId();

        genres = composition.getGenres();
        int genreId;
        for (String genre : genres) {
            try {
                genreId = selectGenreIdByName(genre);
            } catch (StatementDontReturnValueException e) {
                insertIntoGenre(genre);
                genreId = selectCurrentGenreId();
            }

            insertIntoGenreOfComposition(currentCompositionId, genreId);
        }

    }

    // ----------------------------<Розбиття функції insertComposition на менші>----------------------------
    public int selectAssemblageIdByName(String assemblageName) throws VariableIsNull, StatementDontReturnValueException {
        int assemblageID;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if(assemblageName == null)
                throw new VariableIsNull("Змінна з назвою збірки є пустою!");

            preparedStatement = connection.prepareStatement(
                    "SELECT assemblageID FROM Assemblage.AssemblageNames WHERE assemblageName = ?");
            preparedStatement.setString(1, assemblageName);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new StatementDontReturnValueException("Збірки з даним іменем не існує!");

            assemblageID = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } catch (VariableIsNull e) {
            LOG.warn(String.valueOf(e));
            throw new VariableIsNull(e.getMessage());
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }
    public int selectCurrentCompositionId() throws StatementDontReturnValueException {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return compositionId;
    }
    public int selectCurrentAssemblageId() throws StatementDontReturnValueException {
        int assemblageId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(assemblageID) FROM Assemblage.AssemblageNames");
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                throw new  StatementDontReturnValueException("Збірки відсутні в базі даних!");
            assemblageId = resultSet.getInt(1);

        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return assemblageId;
    }
    public int selectCurrentGenreId() throws StatementDontReturnValueException {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                assert preparedStatement != null && resultSet != null;
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return genreId;
    }
    public int selectGenreIdByName(String genre) throws StatementDontReturnValueException{
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }

    // --------------------------------------------------< >--------------------------------------------------
    public List<Composition> getAssemblage(String assemblageName) throws StatementDontReturnValueException, VariableIsNull { // Виводить усі композиції за збіркою.
        List<Composition> assemblage = new LinkedList<>();
        List<String> genreNames = new LinkedList<>();

        int assemblageId = selectAssemblageIdByName(assemblageName);
        List<Composition> compositions = selectCompositionsByAssemblageId(assemblageId);
        List<Integer> genresId;
        for (Composition composition : compositions) {
            try {
                genresId = selectGenresIdByCompositionId(composition.getId());
            } catch (StatementDontReturnValueException e) {
                composition.setAssemblageName(assemblageName);
                assemblage.add(composition);
                continue;
            }
            for (Integer genreId : genresId) {
                String genreName = selectGenreNameById(genreId);
                genreNames.add(genreName);
            }
            composition.setAssemblageName(assemblageName);
            composition.setGenres(genreNames);
            genreNames = new LinkedList<>();

            assemblage.add(composition);
        }
        return assemblage;
    }

    public void updateCompositionById(Composition composition) throws ZeroRowChangedException, VariableIsNull, StatementDontReturnValueException {
        PreparedStatement preparedStatement = null;
        int assemblageId = 0;
        try {
            try {
                assemblageId = selectAssemblageIdByName(composition.getAssemblageName());
            } catch (StatementDontReturnValueException e) {
                insertAssemblageName(composition.getAssemblageName());
                assemblageId = selectCurrentAssemblageId();
            }
            preparedStatement = connection.prepareStatement(
                    "UPDATE Assemblage.Compositions SET name = ?, assemblageId = ?, duration = ?, performer = ? " +
                            "WHERE compositionID = ?");
            preparedStatement.setString(1, composition.getName());
//            preparedStatement.setString(2, composition.getGenre());
            preparedStatement.setInt(2, assemblageId);
            preparedStatement.setFloat(3, (float) composition.getDuration());
            preparedStatement.setString(4, composition.getPerformer());
            preparedStatement.setInt(5, composition.getId());

            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося оновити вказану композицію!");

        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } catch (VariableIsNull e) {
            LOG.warn(String.valueOf(e));
            throw new VariableIsNull(e.getMessage());
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
    }

    // ----------------------------<Розбиття функції getAssemblage на менші>----------------------------
    public List<Composition> selectCompositionsByAssemblageId(int assemblageId) throws StatementDontReturnValueException {
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
                compositions.add(composition);
            } while (resultSet.next());
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return compositions;
    }

    public List<Integer> selectGenresIdByCompositionId(int compositionId) throws StatementDontReturnValueException {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return genresId;
    }

    public String selectGenreNameById(int genreId) throws StatementDontReturnValueException {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return genreName;

    }

    public List<String> selectAssemblageNames() throws StatementDontReturnValueException {
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return assemblageNames;
    }

    public double selectAssemblageTotalDuration(String assemblageName) throws StatementDontReturnValueException {
        double totalDuration;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT totalDuration FROM Assemblage.AssemblageNames WHERE assemblageName = ?");
            preparedStatement.setString(1, assemblageName);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                throw new StatementDontReturnValueException("Загальна тривалість збірок відсутні в базі даних!");

            totalDuration = resultSet.getDouble(1);
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return totalDuration;
    }

    // --------------------------------------------------< >--------------------------------------------------
    public void deleteAssemblage(String assemblageName) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        int assemblageId = selectAssemblageIdByName(assemblageName);
        List<Composition> assemblage = selectCompositionsByAssemblageId(assemblageId);
        for(Composition composition : assemblage) {
            try {
                deleteGenreOfComposition(composition.getId());
            } catch (ZeroRowChangedException ignored) {}
        }
        deleteCompositionsByAssemblageId(assemblageId);
        deleteAssemblageNameById(assemblageId);
    }

    // ----------------------------<Розбиття функції deleteAssemblage на менші>----------------------------

    public void deleteCompositionsByAssemblageId(int assemblageID) throws ZeroRowChangedException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.Compositions WHERE assemblageID = ?")) {
            preparedStatement.setInt(1, assemblageID);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити композиції за вказаним ID збірки!");

        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        }
    }

    public void deleteAssemblageNameById(int assemblageId) throws ZeroRowChangedException {
        try (PreparedStatement preparedDeleteAssemblageStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.AssemblageNames WHERE assemblageID = ?")){
            preparedDeleteAssemblageStatement.setInt(1, assemblageId);
            if(preparedDeleteAssemblageStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити збірку за вказаним ID!");
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        }
    }

    // --------------------------------------------------< >--------------------------------------------------

    public Composition getCompositionById(int compositionID) throws StatementDontReturnValueException {
        List<String> genreNames =  null;
        List<Integer> genresId =  null;
        Composition composition = null;
        String assemblageName = null;
        genreNames = new LinkedList<>();
        genresId = selectGenresIdByCompositionId(compositionID);
        for (int genreId : genresId) {
            genreNames.add(selectGenreNameById(genreId));
        }
        composition = selectCompositionById(compositionID);
        assemblageName = selectAssemblageNameById(Integer.parseInt(composition.getAssemblageName())); ////

        composition.setAssemblageName(assemblageName);
        composition.setGenres(genreNames);
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ignored) {}
        }
        return assemblageName;
    }

// --------------------------------------------------< >--------------------------------------------------

    public void deleteCompositionById(int compositionId) throws ZeroRowChangedException, StatementDontReturnValueException, VariableIsNull {
        Composition composition;
        int assemblageId;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Assemblage.Compositions WHERE compositionID = ?")) {

            composition =  getCompositionById(compositionId);
            deleteGenreOfComposition(compositionId);
            assemblageId = selectAssemblageIdByName(composition.getAssemblageName());
            increaseTotalDurationOfAssemblage((float) -composition.getDuration(), assemblageId);

            preparedStatement.setInt(1, compositionId);
            if(preparedStatement.executeUpdate() == 0)
                throw new ZeroRowChangedException("Не вдалося видалити композиції за вказаним ID!");
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } catch (ZeroRowChangedException e) {
            LOG.warn(String.valueOf(e));
            throw new ZeroRowChangedException(e.getMessage());
        } catch (VariableIsNull e) {
            LOG.warn(String.valueOf(e));
            throw new VariableIsNull(e.getMessage());
        }
    }


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
            LOG.error(e);
            throw new RuntimeException(e);
        } catch (StatementDontReturnValueException e) {
            LOG.warn(String.valueOf(e));
            throw new StatementDontReturnValueException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return compositionsId;
    }
    public List<Composition> getCompositionsByGenreName(String genreName) throws StatementDontReturnValueException {
        int genreId;
        List<Integer> compositionsId = null;
        List<Composition> compositions = new LinkedList<>();

        genreId = selectGenreIdByName(genreName);
        compositionsId = selectCompositionsIdByGenreId(genreId);
        for (int compositionId : compositionsId)
            compositions.add(getCompositionById(compositionId));
        return compositions;
    }
}
