package business.category;

import java.util.List;

public interface CategoryDao {

    public List<Category> findAll();

    public Category findByCategoryId(long categoryId);

    public Category findByCategoryName(String categoryName);

}
