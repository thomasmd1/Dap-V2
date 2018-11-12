package fr.ynov.dap.dap.data.interfaces;

import fr.ynov.dap.dap.data.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
  AppUser findByUserKey(String userKey);

}
