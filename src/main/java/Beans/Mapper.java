package Beans;

import Domain.Game;
import Domain.Hand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public class Mapper implements IMapper {
    public List<IndexDto> toIndexDtos(Game game) {
        List<IndexDto> dtos = new ArrayList<IndexDto>();
        for(Hand hand : game.getHands()){
            dtos.add(new IndexDto(game, hand));
        }
        return dtos;
    }
}
