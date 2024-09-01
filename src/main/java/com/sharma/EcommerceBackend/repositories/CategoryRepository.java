package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByName(String name);

    @Query("Select c from Category c where c.name=:secondLevelCategoryName AND c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("secondLevelCategoryName") String secondLevelCategoryName,@Param("parentCategoryName") String parentCategoryName);
}
