package sg.edu.nus.iss.ssf_day16.controller;

import java.io.Reader;
import java.io.StringReader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path = "/rsvp" , produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPController {

    private Logger logger = Logger.getLogger(RSVPController.class.getName());

// Request
// > POST /rsvp
// > Content-Type: application/x-www-form-urlencoded
// > Accept: application/json
@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public ResponseEntity<String> postAsForm(@RequestBody MultiValueMap<String,String> form){

    String name = form.getFirst("name");
    boolean attending = Boolean.parseBoolean(form.getFirst("attending"));

    logger.log(Level.INFO, "\n >>> name: %s, attending: %b".formatted(name,attending));


    // 201 Created
    // Content-Type: application/json
    //
    // { "status" : "Received <name> RSVP" } 

    // String statusValue = "Received <" + name + "> RSVP";
    // JsonObject payload = Json.createObjectBuilder().add("status",statusValue).build();

    JsonObject payload = Json.createObjectBuilder().add("message","Received %s RSVP".formatted(name))
        .add("attending", attending)
        .build();
    return ResponseEntity.status(201).body(payload.toString());
}


// POST request Content-Type: application/json
@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<String> postAsJson(@RequestBody String payload){

    logger.log(Level.INFO, "\n +++ payload: %s".formatted(payload));

    // read the payload and convert into json object
    Reader reader = new StringReader(payload); 
    JsonReader jsonReader= Json.createReader(reader); 
    JsonObject json = jsonReader.readObject();

    String name = json.getString("name");
    boolean attending = json.getBoolean("attending");

    logger.log(Level.INFO, "\n >>> JSON name: %s, attending: %b".formatted(name,attending));


    // can reuse jsonobject 'json' here
    JsonObject returnPayload = Json.createObjectBuilder().add("message","Received %s RSVP".formatted(name))
        .add("attending", attending)
        .build();

    return ResponseEntity.status(201).body(returnPayload.toString());

}
    
}
