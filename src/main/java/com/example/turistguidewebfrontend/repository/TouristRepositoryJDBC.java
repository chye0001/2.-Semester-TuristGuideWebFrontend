package com.example.turistguidewebfrontend.repository;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepositoryJDBC implements CRUDOperationsJDBC {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<TouristAttraction> getAllAttractions() {
        List<TouristAttraction> attractionList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet attractionsResultSet = executeGetAllAttractionsQuery(connection);

            while (attractionsResultSet.next()) {
                ResultSet tagResultset = executeGetTagsOnAttractionQuery(connection, attractionsResultSet);
                TouristAttraction touristAttraction = recreateAttractionObjectFromDB(attractionsResultSet, tagResultset);
                attractionList.add(touristAttraction);
            }

        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return attractionList;
    }

    public TouristAttraction getAttractionOnName(String touristAttractionName) {
        TouristAttraction requestedAttraction = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet attractionResultset = executeGetAttractionOnNameQuery(connection, touristAttractionName);
            ResultSet tagResultSet = null;
            if (attractionResultset.next()) {
                tagResultSet = executeGetTagsOnAttractionQuery(connection, attractionResultset);
            }

            requestedAttraction = recreateAttractionObjectFromDB(attractionResultset, tagResultSet);

        } catch (SQLException sqlException) {
            System.out.println("Den valgte attraction findes ikke");
            sqlException.printStackTrace();

        }
        return requestedAttraction;
    }

    public void addAttraction(TouristAttraction touristAttraction) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            int cityID = matchCityNameToCityID(connection, touristAttraction);
            PreparedStatement insertAttractionStatement = insertAttractionIntoDB(connection, touristAttraction, cityID);
            int affectedRows = insertAttractionStatement.executeUpdate();

            if (affectedRows > 0) {
                List<Integer> tagIDList = createTagIDListOnAttraction(connection, touristAttraction);
                createAttractionTagRelation(connection, insertAttractionStatement, tagIDList);
            }
        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
    }

    public void updateAttraction(TouristAttraction touristAttraction) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            //Løsning 1 løse dette på enten ved at lave et tjek og finde der hvor der er ændringer

            //Løsning 2 override det hele.
            ResultSet attractionResultSet = executeGetAttractionOnNameQuery(connection, touristAttraction.getName());
            attractionResultSet.next();
            int attractionToUpdateID = attractionResultSet.getInt("ID");

            int cityID = matchCityNameToCityID(connection, touristAttraction);
            int affectedRowsFromUpdate = updateAttractionInDatabase(connection, touristAttraction, attractionToUpdateID, cityID);
            if (affectedRowsFromUpdate > 0) {
                resetAttractionTagRelation(connection, attractionToUpdateID);
            }

            List<Integer> tagIDList = createTagIDListOnAttraction(connection, touristAttraction);
            updateAttractionTagRelation(connection, attractionToUpdateID, tagIDList);


        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
    }

    public void deleteAttraction(String name) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int affectedRowsFromAttractionTagRelation = deleteAttractionTagRelation(connection, name);
            int affectedRowsFromTouristAttractionTable = deleteAttractionFromDatabase(connection, affectedRowsFromAttractionTagRelation, name);

            if (affectedRowsFromTouristAttractionTable < 0 || affectedRowsFromTouristAttractionTable == 0) {
                throw new Error("The Tourist Attraction was not deleted in tourist_attraction table");
            }

        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
    }

    public List<String> getCitySelections() {
        List<String> citySelections = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String getCities = "SELECT name FROM city;";
            PreparedStatement pstmtGetCities = connection.prepareStatement(getCities);
            ResultSet cityResultSet = pstmtGetCities.executeQuery();

            while (cityResultSet.next()) {
                citySelections.add(cityResultSet.getString("name"));
            }
            return citySelections;

        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return citySelections;
    }

    public List<String> getTagSelections() {
        List<String> tagSelections = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String getTags = "SELECT tag FROM tag";
            PreparedStatement pstmtGetTags = connection.prepareStatement(getTags);
            ResultSet tagResultSet = pstmtGetTags.executeQuery();

            while (tagResultSet.next()) {
                tagSelections.add(tagResultSet.getString("tag"));
            }
            return tagSelections;

        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return tagSelections;
    }

    public List<String> getTagsOnAttraction(String attractionName) {
        List<String> tags = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet requestedAttractionResultSet = executeGetAttractionOnNameQuery(connection, attractionName);
            if (requestedAttractionResultSet.next()) {
                ResultSet tagResultSet = executeGetTagsOnAttractionQuery(connection, requestedAttractionResultSet);
                tags = createTagListOnAttraction(tagResultSet);
            }

            return tags;
        } catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return tags;
    }

    private ResultSet executeGetAllAttractionsQuery(Connection connection) throws SQLException {
        String getAttractions =
                "SELECT tourist_attraction.ID,\n" +
                        "\t     tourist_attraction.name, \n" +
                        "\t     tourist_attraction.description, \n" +
                        "       city.name AS city, \n" +
                        "       tourist_attraction.price, \n" +
                        "       tourist_attraction.currencyCode\n" +
                        "       \n" +

                        "FROM tourist_attraction\n" +
                        "JOIN city\n" +
                        "\tON city.ID = tourist_attraction.cityID;";

        PreparedStatement pstmtGetAttractions = connection.prepareStatement(getAttractions);
        return pstmtGetAttractions.executeQuery();
    }

    private ResultSet executeGetTagsOnAttractionQuery(Connection connection, ResultSet resultSet) throws SQLException {
        String getTagsOnAttraction = "" +
                "SELECT tag.tag " +
                "FROM tourist_attraction " +
                "JOIN tourist_attraction_tag " +
                "ON tourist_attraction.ID = tourist_attraction_tag.attractionID " +
                "JOIN tag " +
                "ON tag.ID = tourist_attraction_tag.tagID " +
                "WHERE tourist_attraction.ID = ?";

        PreparedStatement pstmtTags = connection.prepareStatement(getTagsOnAttraction);
        pstmtTags.setInt(1, resultSet.getInt("ID"));
        return pstmtTags.executeQuery();
    }

    private List<String> createTagListOnAttraction(ResultSet tagResultSet) throws SQLException {
        List<String> tags = new ArrayList<>();
        while (tagResultSet.next()) {
            tags.add(tagResultSet.getString("tag"));
        }
        return tags;
    }

    private TouristAttraction recreateAttractionObjectFromDB(ResultSet attractionsResultSet, ResultSet tagResultSet) throws SQLException {
        //Instantiating tags list on attraction
        List<String> tags = createTagListOnAttraction(tagResultSet);

        return new TouristAttraction(
                attractionsResultSet.getString("name"),
                attractionsResultSet.getString("description"),
                attractionsResultSet.getString("city"),
                tags,
                attractionsResultSet.getInt("price"),
                attractionsResultSet.getString("currencyCode"));
    }

    private ResultSet executeGetAttractionOnNameQuery(Connection connection, String touristAttractionName) throws SQLException {
        String getAttractionByName =
                "SELECT tourist_attraction.ID,\n" +
                        "\t   tourist_attraction.name, \n" +
                        "\t   tourist_attraction.description, \n" +
                        "       city.name AS city, \n" +
                        "       tourist_attraction.price, \n" +
                        "       tourist_attraction.currencyCode\n" +
                        "       \n" +
                        "FROM tourist_attraction\n" +
                        "JOIN city\n" +
                        "\tON city.ID = tourist_attraction.cityID\n" +
                        "WHERE tourist_attraction.name = ?;";

        PreparedStatement pstmtGetAttractionOnName = connection.prepareStatement(getAttractionByName);
        pstmtGetAttractionOnName.setString(1, touristAttractionName);
        return pstmtGetAttractionOnName.executeQuery();
    }

    private int matchCityNameToCityID(Connection connection, TouristAttraction touristAttraction) throws SQLException {
        String findCityIdForAttraction = "SELECT * FROM city";
        PreparedStatement pstmtTableCity = connection.prepareStatement(findCityIdForAttraction);
        ResultSet cityNameResultSet = pstmtTableCity.executeQuery();

        int cityID = 0;
        while (cityNameResultSet.next()) {
            if (cityNameResultSet.getString("name").equalsIgnoreCase(touristAttraction.getCity())) {
                cityID = cityNameResultSet.getInt("ID");
            }
        }
        return cityID;
    }

    private List<Integer> createTagIDListOnAttraction(Connection connection, TouristAttraction touristAttraction) throws SQLException {
        List<Integer> tagIDList = new ArrayList<>();
        List<String> attractionTags = touristAttraction.getTags();

        if (!attractionTags.isEmpty()) {
            String findTagIDForAttraction = "SELECT * FROM tag";
            PreparedStatement pstmtTableTag = connection.prepareStatement(findTagIDForAttraction);
            ResultSet matchTagsToTagIDs = pstmtTableTag.executeQuery();

            String tagToRemove = "";
            while (matchTagsToTagIDs.next()) {

                for (String tag : attractionTags) {
                    if (matchTagsToTagIDs.getString("tag").equalsIgnoreCase(tag)) {
                        tagIDList.add(matchTagsToTagIDs.getInt("ID"));
                        tagToRemove = tag;
                        break;
                    }
                }
                attractionTags.remove(tagToRemove);
                if (attractionTags.isEmpty()) {
                    break;
                }
            }
        }
        return tagIDList;
    }

    private PreparedStatement insertAttractionIntoDB(Connection connection, TouristAttraction touristAttraction, int cityID) throws SQLException {
        String insertAttraction = "INSERT INTO tourist_attraction (name, description, cityID, price, currencyCode) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmtAttractionToInsert = connection.prepareStatement(insertAttraction, Statement.RETURN_GENERATED_KEYS);
        pstmtAttractionToInsert.setString(1, touristAttraction.getName());
        pstmtAttractionToInsert.setString(2, touristAttraction.getDescription());
        pstmtAttractionToInsert.setInt(3, cityID);
        pstmtAttractionToInsert.setInt(4, touristAttraction.getPrice());
        pstmtAttractionToInsert.setString(5, touristAttraction.getCurrencyCode());
        return pstmtAttractionToInsert;
    }

    private void createAttractionTagRelation(Connection connection, PreparedStatement insertAttractionStatement, List<Integer> tagIDList) throws SQLException {
        try (ResultSet primaryKeyResultSet = insertAttractionStatement.getGeneratedKeys()) {
            if (primaryKeyResultSet.next()) {
                int createdAttractionID = primaryKeyResultSet.getInt(1);

                String buildAttractionTagRelation = "INSERT INTO tourist_attraction_tag (attractionID, tagID) VALUES (?, ?)";
                PreparedStatement pstmtAttractionTagrelation = connection.prepareStatement(buildAttractionTagRelation);
                for (int tagID : tagIDList) {
                    pstmtAttractionTagrelation.setInt(1, createdAttractionID);
                    pstmtAttractionTagrelation.setInt(2, tagID);
                    pstmtAttractionTagrelation.executeUpdate();
                }

            }
        }
    }

    private int deleteAttractionTagRelation(Connection connection, String name) throws SQLException {
        ResultSet attractionResultSet = executeGetAttractionOnNameQuery(connection, name);

        String deleteAttractionTagRelation = "DELETE FROM tourist_attraction_tag WHERE attractionID = ?";
        PreparedStatement attractionTagRelationToDelete = connection.prepareStatement(deleteAttractionTagRelation);
        if (attractionResultSet.next()) {
            attractionTagRelationToDelete.setInt(1, attractionResultSet.getInt("ID"));
        }

        return attractionTagRelationToDelete.executeUpdate();
    }

    private int deleteAttractionFromDatabase(Connection connection, int affectedRowsFromAttractionTagRelation, String name) throws SQLException {
        if (affectedRowsFromAttractionTagRelation > 0) {
            String deleteAttractionOnName = "DELETE FROM tourist_attraction WHERE name = ?";
            PreparedStatement attractionToDelete = connection.prepareStatement(deleteAttractionOnName);
            attractionToDelete.setString(1, name);
            return attractionToDelete.executeUpdate();

        } else {
            throw new Error("The Tourist Attraction-Tag relation was not deleted");
        }
    }

    private int updateAttractionInDatabase(Connection connection, TouristAttraction touristAttraction, int attractionToUpdateID, int cityID) throws SQLException {
        String updateAttractionInDatabase = "UPDATE tourist_attraction SET description = ?, cityID = ?, price = ? WHERE ID = ?";
        PreparedStatement attractionToUpdate = connection.prepareStatement(updateAttractionInDatabase);
        attractionToUpdate.setString(1, touristAttraction.getDescription());
        attractionToUpdate.setInt(2, cityID);
        attractionToUpdate.setInt(3, touristAttraction.getPrice());
        attractionToUpdate.setInt(4, attractionToUpdateID);
        return attractionToUpdate.executeUpdate();
    }

    private void resetAttractionTagRelation(Connection connection, int attractionToUpdateID) throws SQLException {
        String resetAttractionTagRelation = "DELETE FROM tourist_attraction_tag WHERE attractionID = ?";
        PreparedStatement attractionTagRelationToReset = connection.prepareStatement(resetAttractionTagRelation);
        attractionTagRelationToReset.setInt(1, attractionToUpdateID);
        attractionTagRelationToReset.executeUpdate();
    }

    private void updateAttractionTagRelation(Connection connection, int attractionToUpdateID, List<Integer> tagIDList) throws SQLException {
        String updateAttractionTagRelation = "INSERT INTO tourist_attraction_tag (attractionID, tagID) VALUES (?, ?)";
        PreparedStatement attractionTagRelationToUpdate = connection.prepareStatement(updateAttractionTagRelation);

        for (int tagID : tagIDList) {
            attractionTagRelationToUpdate.setInt(1, attractionToUpdateID);
            attractionTagRelationToUpdate.setInt(2, tagID);
            attractionTagRelationToUpdate.executeUpdate();
        }
    }
}