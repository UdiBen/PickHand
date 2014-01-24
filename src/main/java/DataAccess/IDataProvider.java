package DataAccess;

import Domain.Game;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public interface IDataProvider {
    public Collection<Game> GetGames();
}

