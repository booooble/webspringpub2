package webspringpub.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springinthepub.Pub;

@Controller
public class BaseController {
	
	@RequestMapping(value="/puby", method=RequestMethod.GET)
	public String loadPub(RedirectAttributes model) {
		
		Pub pub = new Pub("Blue Lagoone", 500.0);
	
		model.addFlashAttribute("pub", pub);
		return "pub";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
	   return "index";
	}
	
	@RequestMapping(value="/puby/add", method=RequestMethod.GET)
	public String addVisitor(@ModelAttribute("pub") Pub pub, RedirectAttributes model) throws IOException {
		pub.addRandomVisitorToTheQueue();
		((Pub) model.getFlashAttributes().get("pub")).addRandomVisitorToTheQueue();
		return "pub";
	}
//	   
//	@RequestMapping(value = "pub", method = RequestMethod.GET)
//	public String loadPub(Model m) {
//		m.addAttribute("pub", new Pub("Blue Lagoone", 500.0));
//		
///*		m.addAttribute("pub.visitorsQueue", this.getVisitorsQueue.size());
//*/	   return "pub";
//	}

//	@RequestMapping(value="/pub", method=RequestMethod.POST)
//	public String addBar(@ModelAttribute Pub pub, Model m) {
//
//		return "pub";
//	}
}
