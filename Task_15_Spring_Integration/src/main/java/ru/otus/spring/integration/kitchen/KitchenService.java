package ru.otus.spring.integration.kitchen;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.SpringMaster;

@Service
public class KitchenService {

    public OtusStudent cook(SpringMaster orderItem) throws Exception {
        System.out.println("Cooking " + orderItem.getItemName());
        Thread.sleep(3000);
        System.out.println("Cooking " + orderItem.getItemName() + " done");
        return new OtusStudent(orderItem.getItemName());
    }
}
