/*
 * Builder
 * @author paul
 */
package com.pab.challenge1.builder;

import java.io.Serializable;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Builder {

    <B extends Serializable, RH extends Serializable> B buildBean(RH resourcehelper);

    <B extends Serializable, RH extends Serializable> RH buildResourceHelper(B bean);

    <B extends Serializable, RH extends Serializable> List<B> buildBeanList(List<RH> resourcehelperList);

    <B extends Serializable, RH extends Serializable> List<RH> buildResourceHelper(List<B> beanList);

}