package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.services.google.CalendarService;
import net.minidev.json.JSONObject;

import org.apache.logging.log4j.Logger;
/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {
	
	@Autowired
	private CalendarService calendarService;
	
	final static Logger LOG = LogManager.getLogger(CalendarController.class);
	
	@RequestMapping("/nextEvent")
    public @ResponseBody String nextEvent(@RequestParam(value="userKey", required=true) String userKey) {
        Event lastEvent = null;
        JSONObject response = new JSONObject();
        
        try {
			lastEvent = calendarService.getNextEvent(userKey);
			response.put("Status", lastEvent.getStatus());
			response.put("Date debut", new Date(lastEvent.getStart().getDateTime().getValue()));
			response.put("Date fin", new Date(lastEvent.getEnd().getDateTime().getValue()));
			response.put("Sujet ", lastEvent.getSummary());
			response.put("Self ", lastEvent.getCreator().getSelf());
		} catch (IOException e) {
			LOG.error("Error during getNextEvent" , e);
		}
       
        return response.toString();
    }
}
