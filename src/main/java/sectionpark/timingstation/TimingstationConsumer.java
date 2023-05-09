package sectionpark.timingstation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimingstationConsumer {
	
    @RequestMapping(value="/consumer")
    public String greeting(Model model) {
    	return "consumer";
    }
    
}