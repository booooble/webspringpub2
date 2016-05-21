package webspringpub.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springinthepub.Pub;

@Controller
@SessionAttributes({"pub"})
public class BaseController {
	
	@RequestMapping(value="puby", method=RequestMethod.GET)
	public String loadPub() {
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
	public String addVisitor(@ModelAttribute("pub") Pub pub, final BindingResult mapping1BindingResult, 
			final RedirectAttributes model) throws IOException, InterruptedException {
		pub.addRandomVisitorToBar();
		return "pub";
	}
	
	@RequestMapping(value="add2queue", method=RequestMethod.POST)
	public String addToQueue(@ModelAttribute("pub") Pub pub, final BindingResult mapping1BindingResult, 
			final RedirectAttributes model) throws IOException {
		pub.addRandomVisitorToTheQueue();
		return "pub";
	}
	
	@RequestMapping(value="processQueue", method=RequestMethod.POST)
	public String processQueue(@ModelAttribute("pub") Pub pub, final BindingResult mapping1BindingResult, 
			final RedirectAttributes model) throws IOException, InterruptedException {
		pub.processTheQueue();
		return "pub";
	}
	
	@RequestMapping(value="remove", method=RequestMethod.POST)
	public String removeVisitor(@ModelAttribute("pub") Pub pub, final BindingResult mapping1BindingResult, 
			final RedirectAttributes model) throws IOException {
		pub.removeVisitor();
		return "pub";
	}
	
	@ModelAttribute("pub")
    public Pub getPub() {
        return new Pub("Blue Lagoone", 500.0);
    }
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public void saveHistory(@ModelAttribute("pub") Pub pub, HttpServletRequest request,
					  HttpServletResponse response) throws IOException{
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition",
				"attachment;filename=historyFile.txt");

		String[] list = pub.getHistoryText().toString().split("[\\r\\n]+");
		PrintWriter bwr = response.getWriter();
		
		for (String x : list) {
			bwr.write(x);
			bwr.println();
		}

		bwr.flush();
		bwr.close();
	}
}
