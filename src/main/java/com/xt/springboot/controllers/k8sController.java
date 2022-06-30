package com.xt.springboot.controllers;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.*;
import java.util.stream.Collectors;

import com.xt.springboot.kubernetes.K8sClient;
import com.xt.springboot.models.PodInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class k8sController {

    private final K8sClient k8sClient;

    public k8sController(K8sClient k8sClient) {
        this.k8sClient = k8sClient;
    }

    @GetMapping("/k8s/namespaces/all")
    public List<String> getNamespaces() {
        return k8sClient
                .getNamespaces()
                .stream()
                .map(ns -> ns.getMetadata().getName())
                .collect(Collectors.toList());
    }

    @GetMapping("/namespaces/{tenantId}/pods")
    public List<PodInfo> getPodInformationFrom(String tenantId) {
        StringBuilder sbuilder = new StringBuilder();
        String namespace = sbuilder.append("cloud-engine-").append(tenantId).toString();
        return k8sClient
                .getPodsBy(namespace)
                .stream()
                .map(pod ->
                        new PodInfo(pod.getMetadata().getName(),
                                pod.getMetadata().getLabels().get("chart")))
                .collect(Collectors.toList());
    }

    @GetMapping("/memory")
    public String getMemoryStatus() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        String memoryStats = "";

        String init = String.format("Initial: %.2f GB \n", (double) memoryBean.getHeapMemoryUsage()
                .getInit() / 1073741824);
        String usedHeap = String.format("Used: %.2f GB \n", (double) memoryBean.getHeapMemoryUsage()
                .getUsed() / 1073741824);
        String maxHeap = String.format("Max: %.2f GB \n", (double) memoryBean.getHeapMemoryUsage()
                .getMax() / 1073741824);
        String committed = String.format("Committed: %.2f GB \n", (double) memoryBean.getHeapMemoryUsage()
                .getCommitted() / 1073741824);
        memoryStats += init;
        memoryStats += usedHeap;
        memoryStats += maxHeap;
        memoryStats += committed;

        return memoryStats;
    }
}
