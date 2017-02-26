package com.free.agent.utils;

import com.google.common.collect.ImmutableList;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Set;

public final class PredicateBuilder {
	@SuppressWarnings("unchecked")
	private final ImmutableList.Builder<Predicate> builder = new ImmutableList.Builder();
	private final CriteriaBuilder criteriaBuilder;

	public PredicateBuilder(CriteriaBuilder criteriaBuilder) {
		this.criteriaBuilder = criteriaBuilder;
	}

	public ImmutableList<Predicate> build() {
		return builder.build();
	}

	public Predicate buildWithAndConjunction() {
		Collection<Predicate> predicates = build();

		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	public PredicateBuilder add(Predicate predicate) {
		if (predicate != null) {
			builder.add(predicate);
		}

		return this;
	}

	public <T extends Comparable<? super T>> PredicateBuilder addEqualsPredicate(Path<T> path, T value) {
		return addEqualsPredicate(criteriaBuilder, path, value);
	}

	public <T extends Comparable<? super T>> PredicateBuilder addEqualsPredicate(Path<T> pathLeftSide, Path<T> pathRightSide) {
		builder.add(criteriaBuilder.equal(pathLeftSide, pathRightSide));
		return this;
	}

	public <T extends Comparable<? super T>> PredicateBuilder addEqualsPredicate(CriteriaBuilder criteriaBuilder, Path<T> path, T value) {
		if (value != null) {
			builder.add(criteriaBuilder.equal(path, value));
		}
		return this;
	}

	public <T extends Comparable<? super T>> PredicateBuilder addInPredicate(Path<T> path, Collection<T> values) {
		if (!CollectionUtils.isEmpty(values)) {
			builder.add(path.in(values));
		}
		return this;
	}

	public <T extends Enum<T>> PredicateBuilder addInEnumPredicate(Path<T> path, Set<T> values) {
		if (!CollectionUtils.isEmpty(values) && values.size() < values.iterator().next().getClass().getEnumConstants().length) {
			builder.add(path.in(values));
		}
		return this;
	}

	public <T extends Comparable<? super T>> PredicateBuilder addNotInPredicate(Path<T> path, Collection<T> values) {
		if (!CollectionUtils.isEmpty(values)) {
			builder.add(criteriaBuilder.not(path.in(values)));
		}
		return this;
	}

	public <T extends Comparable<? super T>> PredicateBuilder addRangePredicate(Path<T> path, T from, T to) {
		return addRangePredicate(criteriaBuilder, path, from, to);
	}

	public <T extends Comparable<? super T>> PredicateBuilder addRangePredicate(CriteriaBuilder criteriaBuilder, Path<T> path, T from, T to) {
		if (from != null) {
			if (to != null) {
				builder.add(criteriaBuilder.and(criteriaBuilder.between(path, from, to)));
			} else {
				builder.add(criteriaBuilder.greaterThanOrEqualTo(path, from));
			}
		} else if (to != null) {
			builder.add(criteriaBuilder.lessThanOrEqualTo(path, to));
		}

		return this;
	}

}
