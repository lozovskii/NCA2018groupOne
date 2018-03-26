package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Advert;

import java.util.List;

public interface AdvertService {
    Advert create(Advert advert);

    Advert findById(Long id);

    List<Advert> findAll();

    List<Advert> findAllSortedBy(String field, boolean asc);

    List<Advert> findAllSortedAndFilteredBy(String field, boolean asc, String[] advertTypes);

    List<Advert> findAdvertsWithType(Long id);

    Advert update(Advert advert);

    boolean delete(Advert advert);

    boolean delete(Long id);
}
