package com.example.sqlitedeme.gdb;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.sqlitedeme.bean.FruitsBean;
import com.example.sqlitedeme.bean.User;

import com.example.sqlitedeme.gdb.FruitsBeanDao;
import com.example.sqlitedeme.gdb.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig fruitsBeanDaoConfig;
    private final DaoConfig userDaoConfig;

    private final FruitsBeanDao fruitsBeanDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        fruitsBeanDaoConfig = daoConfigMap.get(FruitsBeanDao.class).clone();
        fruitsBeanDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        fruitsBeanDao = new FruitsBeanDao(fruitsBeanDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(FruitsBean.class, fruitsBeanDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        fruitsBeanDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public FruitsBeanDao getFruitsBeanDao() {
        return fruitsBeanDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}