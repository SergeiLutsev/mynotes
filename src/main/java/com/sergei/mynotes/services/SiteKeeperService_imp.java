package com.sergei.mynotes.services;

import com.sergei.mynotes.domen.SiteKeeper;
import com.sergei.mynotes.repositories.SiteKeeperRepo;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.HashSet;
import java.util.Set;
@Service
public class SiteKeeperService_imp implements SiteKeeperService {
    private final SiteKeeperRepo siteKeeperRepo;

    public SiteKeeperService_imp(SiteKeeperRepo siteKeeperRepo) {
        this.siteKeeperRepo = siteKeeperRepo;
    }

    @Override
    public Set<SiteKeeper> findAll() {
        Set<SiteKeeper> siteKeepers=new HashSet<>();
        siteKeeperRepo.findAll().forEach(siteKeepers::add);
        return siteKeepers;
    }

    @Override
    public SiteKeeper findById(Long id) {
        return siteKeeperRepo.findById(id).orElse(null);
    }

    @Override
    public SiteKeeper save(SiteKeeper siteKeeper) {
        return siteKeeperRepo.save(siteKeeper);
    }

    @Override
    public void saveAllToFIle(String path) {
        File fl=new File(path);
        String sqlData=getAllData();
        try {
            BufferedWriter bf=new BufferedWriter(new FileWriter(fl));
            bf.write(sqlData);
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        siteKeeperRepo.deleteById(id);
    }

    private String getAllData() {
        String txt = "";
        Set<SiteKeeper> keepers=findAll();

      for(SiteKeeper kp : keepers) {
          txt += String.format("insert into site_keeper (name, description, url, note,user_name,password)\n" +
                  "values ('%s','%s','%s', '%s','%s','%s');\n",
                  kp.getName(), kp.getDescription(), kp.getUrl(), Jsoup.parse(kp.getNote()).text(), kp.getLogin().getUserName(), kp.getLogin().getPassword());
      }
        return txt;
    }

    @PreDestroy
    public void createDataFile(){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!It is work!!!!!!!!!!!!!!");
    }
}
