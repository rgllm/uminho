def corrcoef(x, y=None, rowvar=True, bias=np._NoValue, ddof=np._NoValue):
    """
    Return Pearson product-moment correlation coefficients.
    Please refer to the documentation for `cov` for more detail.  The
    relationship between the correlation coefficient matrix, `R`, and the
    covariance matrix, `C`, is
    .. math:: R_{ij} = \\frac{ C_{ij} } { \\sqrt{ C_{ii} * C_{jj} } }
    The values of `R` are between -1 and 1, inclusive.
    Parameters
    ----------
    x : array_like
        A 1-D or 2-D array containing multiple variables and observations.
        Each row of `x` represents a variable, and each column a single
        observation of all those variables. Also see `rowvar` below.
    y : array_like, optional
        An additional set of variables and observations. `y` has the same
        shape as `x`.
    rowvar : bool, optional
        If `rowvar` is True (default), then each row represents a
        variable, with observations in the columns. Otherwise, the relationship
        is transposed: each column represents a variable, while the rows
        contain observations.
    bias : _NoValue, optional
        Has no effect, do not use.
        .. deprecated:: 1.10.0
    ddof : _NoValue, optional
        Has no effect, do not use.
        .. deprecated:: 1.10.0
    Returns
    -------
    R : ndarray
        The correlation coefficient matrix of the variables.
    See Also
    --------
    cov : Covariance matrix
    Notes
    -----
    Due to floating point rounding the resulting array may not be Hermitian,
    the diagonal elements may not be 1, and the elements may not satisfy the
    inequality abs(a) <= 1. The real and imaginary parts are clipped to the
    interval [-1,  1] in an attempt to improve on that situation but is not
    much help in the complex case.
    This function accepts but discards arguments `bias` and `ddof`.  This is
    for backwards compatibility with previous versions of this function.  These
    arguments had no effect on the return values of the function and can be
    safely ignored in this and previous versions of numpy.
    """
    if bias is not np._NoValue or ddof is not np._NoValue:
        # 2015-03-15, 1.10
        warnings.warn('bias and ddof have no effect and are deprecated',
                      DeprecationWarning, stacklevel=2)
    c = cov(x, y, rowvar)
    try:
        d = diag(c)
    except ValueError:
        # scalar covariance
        # nan if incorrect value (nan, inf, 0), 1 otherwise
        return c / c
    stddev = sqrt(d.real)
    c /= stddev[:, None]
    c /= stddev[None, :]

    # Clip real and imaginary parts to [-1, 1].  This does not guarantee
    # abs(a[i,j]) <= 1 for complex arrays, but is the best we can do without
    # excessive work.
    np.clip(c.real, -1, 1, out=c.real)
    if np.iscomplexobj(c):
        np.clip(c.imag, -1, 1, out=c.imag)

return c