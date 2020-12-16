package com.CalculoFinanceiro.domain.model;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ {
	
	public static volatile SingularAttribute<Pessoa, String> nome;
	
	public static volatile SingularAttribute<Pessoa, Integer> idade;

	public static volatile SingularAttribute<Pessoa, BigDecimal> salario;


}
