package webspringpub.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springinthepub.Pub;

@Controller
/*@SessionAttributes("pub")
 * 
*/
@SessionAttributes("pub")
public class BaseController {
	
	@RequestMapping(value="/puby", method=RequestMethod.POST)
	public String loadPub(@ModelAttribute("pub") Pub pub, RedirectAttributes model) {
		
		pub = new Pub("Blue Lagoone", 500.0);
	
		model.addFlashAttribute("pub", pub);
		System.out.println(model.getFlashAttributes());
		
		return "pub";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
	   return "index";
	}
	
	@RequestMapping(value="/puby/add", method=RequestMethod.GET)
	public String addVisitor(@ModelAttribute("pub") Pub pub, final RedirectAttributes model) throws IOException {
/*		System.out.println(pub);
		System.out.println(model.getFlashAttributes());
		Pub p = (Pub) model.getFlashAttributes().get("pub");*/
		pub.addRandomVisitorToTheQueue();
		System.out.println(pub);
		return "pub";
	}
	
	@ModelAttribute("pub")
    public Pub getPub() {
        return new Pub();

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
