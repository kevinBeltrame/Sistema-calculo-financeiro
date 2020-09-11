package com.CalculoFinanceiro.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.model.Pessoa_;
import com.CalculoFinanceiro.repository.filter.PessoaFilter;


public class PessoaRepositoryImpl {


	@PersistenceContext
	private EntityManager manager;
	
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);

		// cria as restrições
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Pessoa> query = manager.createQuery(criteriaQuery);
		
		adicionarRestricoesDePaginacao(query,pageable);

		return new PageImpl<Pessoa>(query.getResultList(), pageable,total(pessoaFilter));		
	}
	
	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder,
			Root<Pessoa> root) {

		List<Predicate> predicates = new ArrayList<>();

		// where descricao like '%djehwa%

		if (!StringUtils.isEmpty(pessoaFilter.getNome())) {
		
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nome)), "%" + pessoaFilter.getNome().toLowerCase() + "%"));																					// em minusculo
		}		

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Pessoa> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual*totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private long total(PessoaFilter pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
}

