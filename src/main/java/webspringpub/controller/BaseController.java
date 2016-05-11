package webspringpub.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springinthepub.Pub;

@Controller
@SessionAttributes({"pub"})
public class BaseController {
	
	@RequestMapping(value="puby", method=RequestMethod.GET)
	public String loadPub() {
		
/*		pub = new Pub("Blue Lagoone", 500.0);
		//model.addAttribute("obj", pub);
		model.addFlashAttribute("pub", pub);
		model.addFlashAttribute("message","Added successfully.");
		System.out.println(model.getFlashAttributes());*/
		
		return "pub";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
	   return "index";
	}
	
	@RequestMapping(value="/pub", method = RequestMethod.GET )
	public String showPubPage()  {
	    return "pub";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addVisitor(@ModelAttribute("pub") Pub pub, final BindingResult mapping1BindingResult, final RedirectAttributes model) throws IOException {
		pub.addRandomVisitorToTheQueue();
		System.out.println("------" + pub);
		return "pub";
	}
	
	@ModelAttribute("pub")
    public Pub getPub() {
        return new Pub("Blue Lagoone", 500.0);

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
