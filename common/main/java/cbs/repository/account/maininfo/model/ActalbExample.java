package cbs.repository.account.maininfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActalbExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public ActalbExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAlcodeIsNull() {
            addCriterion("ALCODE is null");
            return (Criteria) this;
        }

        public Criteria andAlcodeIsNotNull() {
            addCriterion("ALCODE is not null");
            return (Criteria) this;
        }

        public Criteria andAlcodeEqualTo(String value) {
            addCriterion("ALCODE =", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeNotEqualTo(String value) {
            addCriterion("ALCODE <>", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeGreaterThan(String value) {
            addCriterion("ALCODE >", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ALCODE >=", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeLessThan(String value) {
            addCriterion("ALCODE <", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeLessThanOrEqualTo(String value) {
            addCriterion("ALCODE <=", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeLike(String value) {
            addCriterion("ALCODE like", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeNotLike(String value) {
            addCriterion("ALCODE not like", value, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeIn(List<String> values) {
            addCriterion("ALCODE in", values, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeNotIn(List<String> values) {
            addCriterion("ALCODE not in", values, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeBetween(String value1, String value2) {
            addCriterion("ALCODE between", value1, value2, "alcode");
            return (Criteria) this;
        }

        public Criteria andAlcodeNotBetween(String value1, String value2) {
            addCriterion("ALCODE not between", value1, value2, "alcode");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNull() {
            addCriterion("CURCDE is null");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNotNull() {
            addCriterion("CURCDE is not null");
            return (Criteria) this;
        }

        public Criteria andCurcdeEqualTo(String value) {
            addCriterion("CURCDE =", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotEqualTo(String value) {
            addCriterion("CURCDE <>", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThan(String value) {
            addCriterion("CURCDE >", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThanOrEqualTo(String value) {
            addCriterion("CURCDE >=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThan(String value) {
            addCriterion("CURCDE <", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThanOrEqualTo(String value) {
            addCriterion("CURCDE <=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLike(String value) {
            addCriterion("CURCDE like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotLike(String value) {
            addCriterion("CURCDE not like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeIn(List<String> values) {
            addCriterion("CURCDE in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotIn(List<String> values) {
            addCriterion("CURCDE not in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeBetween(String value1, String value2) {
            addCriterion("CURCDE between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotBetween(String value1, String value2) {
            addCriterion("CURCDE not between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andOrgidtIsNull() {
            addCriterion("ORGIDT is null");
            return (Criteria) this;
        }

        public Criteria andOrgidtIsNotNull() {
            addCriterion("ORGIDT is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidtEqualTo(String value) {
            addCriterion("ORGIDT =", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotEqualTo(String value) {
            addCriterion("ORGIDT <>", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThan(String value) {
            addCriterion("ORGIDT >", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThanOrEqualTo(String value) {
            addCriterion("ORGIDT >=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThan(String value) {
            addCriterion("ORGIDT <", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThanOrEqualTo(String value) {
            addCriterion("ORGIDT <=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLike(String value) {
            addCriterion("ORGIDT like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotLike(String value) {
            addCriterion("ORGIDT not like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtIn(List<String> values) {
            addCriterion("ORGIDT in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotIn(List<String> values) {
            addCriterion("ORGIDT not in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtBetween(String value1, String value2) {
            addCriterion("ORGIDT between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotBetween(String value1, String value2) {
            addCriterion("ORGIDT not between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andRectypIsNull() {
            addCriterion("RECTYP is null");
            return (Criteria) this;
        }

        public Criteria andRectypIsNotNull() {
            addCriterion("RECTYP is not null");
            return (Criteria) this;
        }

        public Criteria andRectypEqualTo(String value) {
            addCriterion("RECTYP =", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypNotEqualTo(String value) {
            addCriterion("RECTYP <>", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypGreaterThan(String value) {
            addCriterion("RECTYP >", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypGreaterThanOrEqualTo(String value) {
            addCriterion("RECTYP >=", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypLessThan(String value) {
            addCriterion("RECTYP <", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypLessThanOrEqualTo(String value) {
            addCriterion("RECTYP <=", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypLike(String value) {
            addCriterion("RECTYP like", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypNotLike(String value) {
            addCriterion("RECTYP not like", value, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypIn(List<String> values) {
            addCriterion("RECTYP in", values, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypNotIn(List<String> values) {
            addCriterion("RECTYP not in", values, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypBetween(String value1, String value2) {
            addCriterion("RECTYP between", value1, value2, "rectyp");
            return (Criteria) this;
        }

        public Criteria andRectypNotBetween(String value1, String value2) {
            addCriterion("RECTYP not between", value1, value2, "rectyp");
            return (Criteria) this;
        }

        public Criteria andAlcnamIsNull() {
            addCriterion("ALCNAM is null");
            return (Criteria) this;
        }

        public Criteria andAlcnamIsNotNull() {
            addCriterion("ALCNAM is not null");
            return (Criteria) this;
        }

        public Criteria andAlcnamEqualTo(String value) {
            addCriterion("ALCNAM =", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamNotEqualTo(String value) {
            addCriterion("ALCNAM <>", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamGreaterThan(String value) {
            addCriterion("ALCNAM >", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamGreaterThanOrEqualTo(String value) {
            addCriterion("ALCNAM >=", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamLessThan(String value) {
            addCriterion("ALCNAM <", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamLessThanOrEqualTo(String value) {
            addCriterion("ALCNAM <=", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamLike(String value) {
            addCriterion("ALCNAM like", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamNotLike(String value) {
            addCriterion("ALCNAM not like", value, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamIn(List<String> values) {
            addCriterion("ALCNAM in", values, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamNotIn(List<String> values) {
            addCriterion("ALCNAM not in", values, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamBetween(String value1, String value2) {
            addCriterion("ALCNAM between", value1, value2, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlcnamNotBetween(String value1, String value2) {
            addCriterion("ALCNAM not between", value1, value2, "alcnam");
            return (Criteria) this;
        }

        public Criteria andAlctypIsNull() {
            addCriterion("ALCTYP is null");
            return (Criteria) this;
        }

        public Criteria andAlctypIsNotNull() {
            addCriterion("ALCTYP is not null");
            return (Criteria) this;
        }

        public Criteria andAlctypEqualTo(String value) {
            addCriterion("ALCTYP =", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypNotEqualTo(String value) {
            addCriterion("ALCTYP <>", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypGreaterThan(String value) {
            addCriterion("ALCTYP >", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypGreaterThanOrEqualTo(String value) {
            addCriterion("ALCTYP >=", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypLessThan(String value) {
            addCriterion("ALCTYP <", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypLessThanOrEqualTo(String value) {
            addCriterion("ALCTYP <=", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypLike(String value) {
            addCriterion("ALCTYP like", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypNotLike(String value) {
            addCriterion("ALCTYP not like", value, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypIn(List<String> values) {
            addCriterion("ALCTYP in", values, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypNotIn(List<String> values) {
            addCriterion("ALCTYP not in", values, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypBetween(String value1, String value2) {
            addCriterion("ALCTYP between", value1, value2, "alctyp");
            return (Criteria) this;
        }

        public Criteria andAlctypNotBetween(String value1, String value2) {
            addCriterion("ALCTYP not between", value1, value2, "alctyp");
            return (Criteria) this;
        }

        public Criteria andColnumIsNull() {
            addCriterion("COLNUM is null");
            return (Criteria) this;
        }

        public Criteria andColnumIsNotNull() {
            addCriterion("COLNUM is not null");
            return (Criteria) this;
        }

        public Criteria andColnumEqualTo(String value) {
            addCriterion("COLNUM =", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumNotEqualTo(String value) {
            addCriterion("COLNUM <>", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumGreaterThan(String value) {
            addCriterion("COLNUM >", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumGreaterThanOrEqualTo(String value) {
            addCriterion("COLNUM >=", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumLessThan(String value) {
            addCriterion("COLNUM <", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumLessThanOrEqualTo(String value) {
            addCriterion("COLNUM <=", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumLike(String value) {
            addCriterion("COLNUM like", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumNotLike(String value) {
            addCriterion("COLNUM not like", value, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumIn(List<String> values) {
            addCriterion("COLNUM in", values, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumNotIn(List<String> values) {
            addCriterion("COLNUM not in", values, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumBetween(String value1, String value2) {
            addCriterion("COLNUM between", value1, value2, "colnum");
            return (Criteria) this;
        }

        public Criteria andColnumNotBetween(String value1, String value2) {
            addCriterion("COLNUM not between", value1, value2, "colnum");
            return (Criteria) this;
        }

        public Criteria andLinflgIsNull() {
            addCriterion("LINFLG is null");
            return (Criteria) this;
        }

        public Criteria andLinflgIsNotNull() {
            addCriterion("LINFLG is not null");
            return (Criteria) this;
        }

        public Criteria andLinflgEqualTo(String value) {
            addCriterion("LINFLG =", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgNotEqualTo(String value) {
            addCriterion("LINFLG <>", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgGreaterThan(String value) {
            addCriterion("LINFLG >", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgGreaterThanOrEqualTo(String value) {
            addCriterion("LINFLG >=", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgLessThan(String value) {
            addCriterion("LINFLG <", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgLessThanOrEqualTo(String value) {
            addCriterion("LINFLG <=", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgLike(String value) {
            addCriterion("LINFLG like", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgNotLike(String value) {
            addCriterion("LINFLG not like", value, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgIn(List<String> values) {
            addCriterion("LINFLG in", values, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgNotIn(List<String> values) {
            addCriterion("LINFLG not in", values, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgBetween(String value1, String value2) {
            addCriterion("LINFLG between", value1, value2, "linflg");
            return (Criteria) this;
        }

        public Criteria andLinflgNotBetween(String value1, String value2) {
            addCriterion("LINFLG not between", value1, value2, "linflg");
            return (Criteria) this;
        }

        public Criteria andCredatIsNull() {
            addCriterion("CREDAT is null");
            return (Criteria) this;
        }

        public Criteria andCredatIsNotNull() {
            addCriterion("CREDAT is not null");
            return (Criteria) this;
        }

        public Criteria andCredatEqualTo(Date value) {
            addCriterion("CREDAT =", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatNotEqualTo(Date value) {
            addCriterion("CREDAT <>", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatGreaterThan(Date value) {
            addCriterion("CREDAT >", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatGreaterThanOrEqualTo(Date value) {
            addCriterion("CREDAT >=", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatLessThan(Date value) {
            addCriterion("CREDAT <", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatLessThanOrEqualTo(Date value) {
            addCriterion("CREDAT <=", value, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatIn(List<Date> values) {
            addCriterion("CREDAT in", values, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatNotIn(List<Date> values) {
            addCriterion("CREDAT not in", values, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatBetween(Date value1, Date value2) {
            addCriterion("CREDAT between", value1, value2, "credat");
            return (Criteria) this;
        }

        public Criteria andCredatNotBetween(Date value1, Date value2) {
            addCriterion("CREDAT not between", value1, value2, "credat");
            return (Criteria) this;
        }

        public Criteria andAlbbalIsNull() {
            addCriterion("ALBBAL is null");
            return (Criteria) this;
        }

        public Criteria andAlbbalIsNotNull() {
            addCriterion("ALBBAL is not null");
            return (Criteria) this;
        }

        public Criteria andAlbbalEqualTo(Long value) {
            addCriterion("ALBBAL =", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalNotEqualTo(Long value) {
            addCriterion("ALBBAL <>", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalGreaterThan(Long value) {
            addCriterion("ALBBAL >", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalGreaterThanOrEqualTo(Long value) {
            addCriterion("ALBBAL >=", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalLessThan(Long value) {
            addCriterion("ALBBAL <", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalLessThanOrEqualTo(Long value) {
            addCriterion("ALBBAL <=", value, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalIn(List<Long> values) {
            addCriterion("ALBBAL in", values, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalNotIn(List<Long> values) {
            addCriterion("ALBBAL not in", values, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalBetween(Long value1, Long value2) {
            addCriterion("ALBBAL between", value1, value2, "albbal");
            return (Criteria) this;
        }

        public Criteria andAlbbalNotBetween(Long value1, Long value2) {
            addCriterion("ALBBAL not between", value1, value2, "albbal");
            return (Criteria) this;
        }

        public Criteria andAstrblIsNull() {
            addCriterion("ASTRBL is null");
            return (Criteria) this;
        }

        public Criteria andAstrblIsNotNull() {
            addCriterion("ASTRBL is not null");
            return (Criteria) this;
        }

        public Criteria andAstrblEqualTo(Long value) {
            addCriterion("ASTRBL =", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblNotEqualTo(Long value) {
            addCriterion("ASTRBL <>", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblGreaterThan(Long value) {
            addCriterion("ASTRBL >", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblGreaterThanOrEqualTo(Long value) {
            addCriterion("ASTRBL >=", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblLessThan(Long value) {
            addCriterion("ASTRBL <", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblLessThanOrEqualTo(Long value) {
            addCriterion("ASTRBL <=", value, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblIn(List<Long> values) {
            addCriterion("ASTRBL in", values, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblNotIn(List<Long> values) {
            addCriterion("ASTRBL not in", values, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblBetween(Long value1, Long value2) {
            addCriterion("ASTRBL between", value1, value2, "astrbl");
            return (Criteria) this;
        }

        public Criteria andAstrblNotBetween(Long value1, Long value2) {
            addCriterion("ASTRBL not between", value1, value2, "astrbl");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNull() {
            addCriterion("RECSTS is null");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNotNull() {
            addCriterion("RECSTS is not null");
            return (Criteria) this;
        }

        public Criteria andRecstsEqualTo(String value) {
            addCriterion("RECSTS =", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotEqualTo(String value) {
            addCriterion("RECSTS <>", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThan(String value) {
            addCriterion("RECSTS >", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThanOrEqualTo(String value) {
            addCriterion("RECSTS >=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThan(String value) {
            addCriterion("RECSTS <", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThanOrEqualTo(String value) {
            addCriterion("RECSTS <=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLike(String value) {
            addCriterion("RECSTS like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotLike(String value) {
            addCriterion("RECSTS not like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsIn(List<String> values) {
            addCriterion("RECSTS in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotIn(List<String> values) {
            addCriterion("RECSTS not in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsBetween(String value1, String value2) {
            addCriterion("RECSTS between", value1, value2, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotBetween(String value1, String value2) {
            addCriterion("RECSTS not between", value1, value2, "recsts");
            return (Criteria) this;
        }

        public Criteria andSumflgIsNull() {
            addCriterion("SUMFLG is null");
            return (Criteria) this;
        }

        public Criteria andSumflgIsNotNull() {
            addCriterion("SUMFLG is not null");
            return (Criteria) this;
        }

        public Criteria andSumflgEqualTo(String value) {
            addCriterion("SUMFLG =", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgNotEqualTo(String value) {
            addCriterion("SUMFLG <>", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgGreaterThan(String value) {
            addCriterion("SUMFLG >", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgGreaterThanOrEqualTo(String value) {
            addCriterion("SUMFLG >=", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgLessThan(String value) {
            addCriterion("SUMFLG <", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgLessThanOrEqualTo(String value) {
            addCriterion("SUMFLG <=", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgLike(String value) {
            addCriterion("SUMFLG like", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgNotLike(String value) {
            addCriterion("SUMFLG not like", value, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgIn(List<String> values) {
            addCriterion("SUMFLG in", values, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgNotIn(List<String> values) {
            addCriterion("SUMFLG not in", values, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgBetween(String value1, String value2) {
            addCriterion("SUMFLG between", value1, value2, "sumflg");
            return (Criteria) this;
        }

        public Criteria andSumflgNotBetween(String value1, String value2) {
            addCriterion("SUMFLG not between", value1, value2, "sumflg");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTALB
     *
     * @mbggenerated do_not_delete_during_merge Sun Nov 21 21:36:05 CST 2010
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}