package com.example.demo.service;
import com.example.demo.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.List;

@Service
public class MessagingService {


    @Autowired
    private IMqttClient mqttClient;

    private String latitude;
    private String longitude;

    InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086");

    public void subscribe(final String topic) throws MqttException, InterruptedException {
        System.out.println("Messages received:");

        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            String jsonlocation = new String(msg.getPayload());
            ObjectMapper mapper = new ObjectMapper();
            Location loca = mapper.readValue(jsonlocation, Location.class);
            latitude = loca.getY();
            longitude = loca.getX();
            saveData(latitude,longitude);
            System.out.println(msg.getId() + " -> " + jsonlocation);
        });
    }

    private void saveData(String lat, String lng) throws Exception{
        //InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086");
        /*
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        */

        Point.Builder builder = Point.measurement("gpsdata");
        builder.time(System.currentTimeMillis(), TimeUnit.MICROSECONDS);
        builder.addField("lat",lat);
        builder.addField("lng",lng);
        builder.tag("device-id","rover");
        Point point = builder.build();
        influxDB.setDatabase("mydatabase").write(point);

        /*
        Query queryObject = new Query("select * from gpsdata1", "mydatabase");
        QueryResult queryResult = influxDB.query(queryObject);

        List<QueryResult.Result> results = queryResult.getResults();
        if (results == null) {
            System.out.println("null.");
        }
        // 多个sql用分号隔开，因本次查询只有一个sql，所以取第一个就行
        QueryResult.Result result = results.get(0);
        List<QueryResult.Series> seriesList = result.getSeries();
        for (QueryResult.Series series : seriesList) {
            if (series == null) {
                System.out.println("null.");
            }
            System.out.println("colums ==>> " + series.getColumns());
            System.out.println("tags ==>> " + series.getTags());
            System.out.println("name ==>> " + series.getName());
            System.out.println("values ==>> " + series.getValues());
            System.out.println("查询总数为： ==>> " + (series.getValues() == null ? 0 : series.getValues().size()));
        }
        */

    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }



}
