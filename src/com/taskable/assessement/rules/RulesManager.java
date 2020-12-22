package com.taskable.assessement.rules;

import com.taskable.assessement.Constants;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class RulesManager {
    private List<Rule> rulesList = new ArrayList<>();
    private static RulesManager ourInstance;

    static {
        try {
            ourInstance = new RulesManager();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (BadParameterException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static RulesManager getInstance() {
        return ourInstance;
    }

    private RulesManager() throws NullParameterException, BadParameterException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (String ruleClass : Constants.RuleClasses) {
            Class<?> c = Class.forName(ruleClass);
            Constructor<?> cons = c.getConstructor();
            Rule rule = (Rule) cons.newInstance();
            rulesList.add(rule);
        }
    }

    public List<Rule> getRulesList() {
        return rulesList;
    }
}
