# To run
    - mvn clean install -DskipTests spring-boot:run
    - server will start on port 9911
# API
    ## without sort
        - curl -XGET 'localhost:9911/hotels/search?query=Bangkok'
        - Example response list of hotels
            [{"hotelId":1,"city":"Bangkok","roomType":"DELUXE","price":1000},...]

    ## without sort default ascending
        - curl -XGET 'localhost:9911/hotels/search?query=Bangkok&sortByPrice=true'
        - Example response list of hotels
            [{"hotelId":11,"city":"Bangkok","roomType":"DELUXE","price":60},...]

        - curl -XGET 'localhost:9911/hotels/search?query=Bangkok&sortByPrice=true&isAscending=false'
        - Example response list of hotels
            [{"hotelId":14,"city":"Bangkok","roomType":"SWEET_SUITE","price":25000},...]

# Test Cases
    - Some of the test cases are added in SearchControllerTest, SearchControllerRateLimiterTest, SearchRepositoryTest
    - If you require extensive API test cases, I can send you a java or nodejs program for that.
