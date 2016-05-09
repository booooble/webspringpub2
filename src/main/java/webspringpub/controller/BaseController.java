package webspringpub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springinthepub.Pub;

@Controller
public class BaseController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
	   return "index";
	}
	   
	@RequestMapping(value = "pub", method = RequestMethod.GET)
	public String loadPub(Model m) {
		m.addAttribute("pub", new Pub("Blue Lagoone", 500.0));
		
/*		m.addAttribute("pub.visitorsQueue", this.getVisitorsQueue.size());
*/	   return "pub";
	}

	@RequestMapping(value="/pub", method=RequestMethod.POST)
	public String addBar(@ModelAttribute Pub pub, Model m) {

		return "pub";
	}
}
