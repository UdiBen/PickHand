package Beans;

import Domain.Game;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public interface IMapper {
    public List<HandIndexDto> toIndexDtos(Game game);
}
