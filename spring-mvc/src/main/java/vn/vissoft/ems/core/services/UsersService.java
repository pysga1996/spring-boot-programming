package vn.vissoft.ems.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.dto.LoginDTO;
import vn.vissoft.ems.core.helper.RepoCommonUtil;
import vn.vissoft.ems.core.model.User;
import vn.vissoft.ems.core.repo.UsersRepository;

import javax.transaction.Transactional;

@Service
public class UsersService {

  @Autowired
  private UsersRepository usersRepository;

  // START - BLOCK FOR LOGIN
  public User findUserByUserId(Long userId) {
    Object obj = usersRepository.findByUserId(userId);
    if (obj != null) {
      return toEntity(obj);
    }

    return null;
  }

  public LoginDTO findByUserId(Long userId) {
    Object obj = usersRepository.findByUserId(userId);
    if (obj != null) {
      return toLoginDTO(obj);
    }

    return null;
  }

  public LoginDTO findByUserName(String userName) {
    Object obj = usersRepository.findByUserName(userName);
    if (obj != null) {
      return toLoginDTO(obj);
    }

    return null;
  }

  public User findUserByUserName(String userName) {
    Object obj = usersRepository.findByUserName(userName);
    if (obj != null) {
      return toEntity(obj);
    }

    return null;
  }

  private LoginDTO toLoginDTO(Object useObj) {
    LoginDTO loginDTO = new LoginDTO();
    int index = 0;
    // USER INFO
    Object[] user = (Object[]) useObj;
    loginDTO.setUserId(RepoCommonUtil.getLongFromBigInteger(user[index++]));
    loginDTO.setCode(RepoCommonUtil.getStringByValue(user[index++]));
    loginDTO.setUserName(RepoCommonUtil.getStringByValue(user[index++]));
    loginDTO.setEmail(RepoCommonUtil.getStringByValue(user[index++]));
    loginDTO.setPassword(RepoCommonUtil.getStringByValue(user[index++]));
    loginDTO.setLocked(RepoCommonUtil.getIntegerByValue(user[index++]));

    return loginDTO;
  }

  private User toEntity(Object useObj) {
    User userRes = new User();
    int index = 0;
    // USER INFO
    Object[] user = (Object[]) useObj;
    userRes.setUserId(RepoCommonUtil.getLongFromBigInteger(user[index++]));
    userRes.setCode(RepoCommonUtil.getStringByValue(user[index++]));
    userRes.setUserName(RepoCommonUtil.getStringByValue(user[index++]));
    userRes.setEmail(RepoCommonUtil.getStringByValue(user[index++]));
    userRes.setPassword(RepoCommonUtil.getStringByValue(user[index++]));
    userRes.setLocked(RepoCommonUtil.getActive(user[index++]));

    return userRes;
  }
  // END - BLOCK FOR LOGIN

  @Transactional
  public void deleteById(Long userId) {
    usersRepository.deleteById(userId);
  }
}
