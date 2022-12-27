London Shop Project <br />
tools:<br />
  -spring boot<br />
  -postgres 14 database<br />
 
create database name "londonShopDB"<br />
please update the username and password in application. properties file:<br />
  spring.datasource.username=postgres<br />
  spring.datasource.password=admin<br />
the application runs on port 8081<br />

i provided a swagger for the endpoints:<br />
http://localhost:8081/london-shop/v1/swagger-ui/index.html<br />

there is additional api which is "/randomDevices" please hit it to add the devices in the database
