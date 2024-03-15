package com.example.turistguidewebfrontend.repository;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepositoryJDBC {

//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;

//    private final String url = "jdbc:mysql://touristattractionserver.mysql.database.azure.com/tourist_guide";
//    private final String username = "newuser1";
//    private final String password = "password1_";

    private final String url = "jdbc:mysql://localhost:3306/tourist_guide";
    private final String username = "newuser1";
    private final String password = "newuser1";

    public List<TouristAttraction> getAllAttractions() {
        List<TouristAttraction> attractionList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            ResultSet attractionsResultSet = executeGetAllAttractionsQuery(connection);

            while (attractionsResultSet.next()){
                ResultSet tagResultset = executeGetTagsOnAttractionQuery(connection, attractionsResultSet);
                TouristAttraction touristAttraction = recreateAttractionObjectFromDB(attractionsResultSet, tagResultset);
                attractionList.add(touristAttraction);
            }
            return attractionList;

        }catch (SQLException sqlException){
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return attractionList;
    }

    public TouristAttraction read(String touristAttractionName){
        TouristAttraction requestedAttraction = null;

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            ResultSet attractionResultset = executeGetAttractionOnNameQuery(connection, touristAttractionName);
            ResultSet tagResultSet = executeGetTagsOnAttractionQuery(connection, attractionResultset);

            requestedAttraction = recreateAttractionObjectFromDB(attractionResultset, tagResultSet);
            return requestedAttraction;

        }catch (SQLException sqlException){
            System.out.println("Den valgte attraction findes ikke");
            sqlException.printStackTrace();

        }
        return requestedAttraction;
    }

    public void create(TouristAttraction touristAttraction){
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String findCityIdForAttraction = "SELECT * FROM city";
            PreparedStatement pstmtTableCity = connection.prepareStatement(findCityIdForAttraction);
            ResultSet matchCityNameToCityID = pstmtTableCity.executeQuery();

            int cityID = 0;
            while (matchCityNameToCityID.next()){
                if (matchCityNameToCityID.getString("name").equalsIgnoreCase(touristAttraction.getCity())){
                    cityID = matchCityNameToCityID.getInt("ID");
                }
            }





            String findTagIDForAttraction = "SELECT * FROM tag";
            PreparedStatement pstmtTableTag = connection.prepareStatement(findTagIDForAttraction);
            ResultSet matchTagsToTagIDs = pstmtTableTag.executeQuery();

            List<Integer> tagIDList = new ArrayList<>();
            while (matchTagsToTagIDs.next()){
                for (String tag:touristAttraction.getTags()) {
                    if (matchTagsToTagIDs.getString("tag").equalsIgnoreCase(tag)){
                        tagIDList.add(matchTagsToTagIDs.getInt("ID"));
                    }
                }
            }

            String insertAttraction = "INSERT INTO tourist_attraction (name, description, cityID, price, currencyCode) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmtTableAttraction = connection.prepareStatement(insertAttraction, Statement.RETURN_GENERATED_KEYS);
            pstmtTableAttraction.setString(1, touristAttraction.getName());
            pstmtTableAttraction.setString(2, touristAttraction.getDescription());
            pstmtTableAttraction.setInt(3, cityID);
            pstmtTableAttraction.setInt(4,touristAttraction.getPrice());
            pstmtTableAttraction.setString(5, touristAttraction.getCurrencyCode());

            int affectedRows = pstmtTableAttraction.executeUpdate();

            if (affectedRows > 0){
                try(ResultSet primaryKeyResultSet = pstmtTableAttraction.getGeneratedKeys()){
                    if (primaryKeyResultSet.next()){
                        int createdAttractionID = primaryKeyResultSet.getInt(1);

                        String buildAttractionTagRelation = "INSERT INTO tourist_attraction_tag (attractionID, tagID) VALUES (?, ?)";
                        PreparedStatement pstmtAttractionTagrelation = connection.prepareStatement(buildAttractionTagRelation);
                        for (int tagID:tagIDList) {
                            pstmtAttractionTagrelation.setInt(1, createdAttractionID);
                            pstmtAttractionTagrelation.setInt(2, tagID);
                            pstmtAttractionTagrelation.executeUpdate();
                        }

                    }
                }
            }

        }catch (SQLException sqlException){
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
    }

//
//    public TouristAttraction update(TouristAttraction touristAttraction){
//        TouristAttraction updatedTouristAttraction = null;
//        return updatedTouristAttraction;
//    }
//
//    public TouristAttraction delete(String name){
//        TouristAttraction touristAttractionToDelete = null;
//
//
//        return touristAttractionToDelete;
//    }
//
    public List<String> getCitySelections(){
        List<String> citySelections = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)){
            String getCities = "SELECT name FROM city;";
            PreparedStatement pstmtGetCities = connection.prepareStatement(getCities);
            ResultSet cityResultSet = pstmtGetCities.executeQuery();

            while (cityResultSet.next()){
                citySelections.add(cityResultSet.getString("name"));
            }
            return citySelections;

        }catch (SQLException sqlException){
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return citySelections;
    }

    public List<String> getTagSelections(){
        List<String> tagSelections = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)){
            String getTags = "SELECT tag FROM tag";
            PreparedStatement pstmtGetTags = connection.prepareStatement(getTags);
            ResultSet tagResultSet = pstmtGetTags.executeQuery();

            while (tagResultSet.next()){
                tagSelections.add(tagResultSet.getString("tag"));
            }
            return tagSelections;

        }catch (SQLException sqlException){
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return tagSelections;
    }

    public List<String> getTagsOnAttraction(String attractionName) {
        List<String> tags = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet requestedAttractionResultSet = executeGetAttractionOnNameQuery(connection, attractionName);
            if (requestedAttractionResultSet.next()) {
                ResultSet tagResultSet = executeGetTagsOnAttractionQuery(connection, requestedAttractionResultSet);
                tags = createTagListOnAttraction(tagResultSet);
            }

            return tags;
        }catch (SQLException sqlException) {
            System.out.println("Noget gik galt");
            sqlException.printStackTrace();
        }
        return tags;
    }

    private ResultSet executeGetAllAttractionsQuery(Connection connection) throws SQLException{
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

        TouristAttraction touristAttraction = new TouristAttraction(
                attractionsResultSet.getString("name"),
                attractionsResultSet.getString("description"),
                attractionsResultSet.getString("city"),
                tags,
                attractionsResultSet.getInt("price"),
                attractionsResultSet.getString("currencyCode"));
        return touristAttraction;
    }

    private ResultSet executeGetAttractionOnNameQuery(Connection connection, String touristAttractionName) throws SQLException {
        String getAttractionByName =
                "SELECT tourist_attraction.ID,\n" +
                        "\t   tourist_attraction.name as Attraction, \n" +
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
}
