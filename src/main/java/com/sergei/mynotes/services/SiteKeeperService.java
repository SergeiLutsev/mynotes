package com.sergei.mynotes.services;

import com.sergei.mynotes.domen.SiteKeeper;

import java.util.Set;

public interface SiteKeeperService {
    Set<SiteKeeper> findAll();
    SiteKeeper findById(Long id);
    SiteKeeper save(SiteKeeper siteKeeper);
    void saveAllToFIle(String path);
    void deleteById(Long id);
}
