package com.sergei.mynotes.controllers;

import com.sergei.mynotes.domen.Login;
import com.sergei.mynotes.domen.SiteKeeper;
import com.sergei.mynotes.services.SiteKeeperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

@Slf4j
@Controller
public class SiteKeeperController {
    private final SiteKeeperService siteKeeperService;

    public SiteKeeperController(SiteKeeperService siteKeeperService) {
        this.siteKeeperService = siteKeeperService;
    }

    @GetMapping({"","/","/index","/index.html"})
    public String getIndex(Model model){
        model.addAttribute("sites", siteKeeperService.findAll());
        return "/index";
    }

    @GetMapping("/sitekeeper/{siteId}/show")
    private String showSitekeeper(@PathVariable String siteId, Model model){
        model.addAttribute("siteKeper",siteKeeperService.findById(Long.valueOf(siteId)));
        return "/sitekeeper/show";
    }
    @GetMapping("/sitekeeper/{siteId}/update")
    private String updateSitekeeper(@PathVariable String siteId, Model model){
        model.addAttribute("sitekeeper",siteKeeperService.findById(Long.valueOf(siteId)));
        return "sitekeeper/editform";
    }
    @GetMapping("/sitekeeper/create")
    private String createSitekeeper(Model model){
        SiteKeeper sk=new SiteKeeper();
        sk.setLogin(new Login());
        model.addAttribute("sitekeeper",sk);
        return "sitekeeper/editform";
    }

    @PostMapping("/sitekeeper/update")
    private String saveOrUpdate(@ModelAttribute SiteKeeper siteKeeper){
        log.debug("try to save");
        SiteKeeper site=siteKeeperService.save(siteKeeper);
        return "redirect:/sitekeeper/"+site.getId()+"/show";
    }

    @GetMapping("sitekeeper/savetofile")
    private String saveAllDataToFile(){
        String path= "src"+File.separator+"main"+File.separator+"resources"+File.separator+"data.sql";
        siteKeeperService.saveAllToFIle(path);
        return "redirect:/index";
    }

    @GetMapping("/sitekeeper/{siteId}/delete")
    private String deleteSitekeeper(@PathVariable String siteId){
        siteKeeperService.deleteById(Long.valueOf(siteId));
        return "redirect:/index";
    }
}
