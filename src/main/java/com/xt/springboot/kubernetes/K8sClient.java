package com.xt.springboot.kubernetes;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class K8sClient {

    private KubernetesClient kubernetesClient;

    public K8sClient() {
        this.kubernetesClient = new DefaultKubernetesClient();
    }

    public List<Namespace> getNamespaces() {
        return kubernetesClient.namespaces().list().getItems();
    }

    public List<Pod> getPodsBy(String namespace) {
        return kubernetesClient
                .pods()
                .inNamespace(namespace)
                .list()
                .getItems();
    }
}
