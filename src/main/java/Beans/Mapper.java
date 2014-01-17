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
    public List<HandIndexDto> toIndexDtos(Game game) {
        List<HandIndexDto> dtos = new ArrayList<HandIndexDto>();
        for(Hand hand : game.getHands()){
            dtos.add(new HandIndexDto(game, hand));
        }
        return dtos;
    }
}
