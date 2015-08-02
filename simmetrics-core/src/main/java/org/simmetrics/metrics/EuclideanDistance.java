/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.metrics;

import static java.util.Collections.frequency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.ListDistance;
import org.simmetrics.ListMetric;

import static java.lang.Math.sqrt;

/**
 * Euclidean Distance algorithm providing a similarity measure between two lists
 * using the vector space of combined terms as the dimensions.
 *
 * <p>
 * This class is immutable and thread-safe.
 * @param <T>
 *            type of the token
 * 
 */
public class EuclideanDistance<T> implements ListMetric<T>, ListDistance<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		float totalPossible = (float) sqrt((a.size() * a.size()) + (b.size() * b.size()));
		return (totalPossible - distance(a, b)) / totalPossible;
	}

	@Override
	public float distance(final List<T> a, final List<T> b) {
		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		float totalDistance = 0.0f;
		for (final T token : all) {
			int frequencyInA = frequency(a, token);
			int frequencyInB = frequency(b, token);

			totalDistance += ((frequencyInA - frequencyInB) * (frequencyInA - frequencyInB));
		}

		return (float) sqrt(totalDistance);
	}

	@Override
	public String toString() {
		return "EuclideanDistance";
	}

}
