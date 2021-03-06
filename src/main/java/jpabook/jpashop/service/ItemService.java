package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // 해당 어노테이션이 없으면 위에서 전체적으로 readOnly = true로 뒀기 때문에 수정이 불가능함
    // 하지만 명시를 하면 false (원래 Transactional의 기본 값)으로 줄 수 있음
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice()); // Param에서 필요한 걸 꺼냄
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        // itemRepository.save(findItem); 다음을 호출 할 필요가 없음
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
