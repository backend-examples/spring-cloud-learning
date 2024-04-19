package com.example.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @SentinelResource(value = "service", blockHandler = "blockHandlerMethod", fallback = "fallbackMethod")
    public void test() {
        System.out.println("service");
    }

    public String blockHandlerMethod(String status, BlockException ex) {
        System.out.println("---blockHandlerMethod---");
        return "--blockHandlerMethod方法--";
    }

    public String fallbackMethod(String status) {
        System.out.println("---fallbackMethod---");
        return "--fallbackMethod方法--";
    }


//    /**
//     * 初始化保护规则
//     *
//     *  @PostConstruct会在IoC装配完成之后执行
//     */
//    @PostConstruct
//    private void initFlowQpsRule() {
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule("service");
//        // set limit qps to 20
//        rule.setCount(20);
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setLimitApp("default");
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }
}
