package cbs.repository.code.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActtscExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public ActtscExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
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
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
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

        public Criteria andTddtdcIsNull() {
            addCriterion("TDDTDC is null");
            return (Criteria) this;
        }

        public Criteria andTddtdcIsNotNull() {
            addCriterion("TDDTDC is not null");
            return (Criteria) this;
        }

        public Criteria andTddtdcEqualTo(String value) {
            addCriterion("TDDTDC =", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcNotEqualTo(String value) {
            addCriterion("TDDTDC <>", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcGreaterThan(String value) {
            addCriterion("TDDTDC >", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcGreaterThanOrEqualTo(String value) {
            addCriterion("TDDTDC >=", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcLessThan(String value) {
            addCriterion("TDDTDC <", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcLessThanOrEqualTo(String value) {
            addCriterion("TDDTDC <=", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcLike(String value) {
            addCriterion("TDDTDC like", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcNotLike(String value) {
            addCriterion("TDDTDC not like", value, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcIn(List<String> values) {
            addCriterion("TDDTDC in", values, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcNotIn(List<String> values) {
            addCriterion("TDDTDC not in", values, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcBetween(String value1, String value2) {
            addCriterion("TDDTDC between", value1, value2, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddtdcNotBetween(String value1, String value2) {
            addCriterion("TDDTDC not between", value1, value2, "tddtdc");
            return (Criteria) this;
        }

        public Criteria andTddnamIsNull() {
            addCriterion("TDDNAM is null");
            return (Criteria) this;
        }

        public Criteria andTddnamIsNotNull() {
            addCriterion("TDDNAM is not null");
            return (Criteria) this;
        }

        public Criteria andTddnamEqualTo(String value) {
            addCriterion("TDDNAM =", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamNotEqualTo(String value) {
            addCriterion("TDDNAM <>", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamGreaterThan(String value) {
            addCriterion("TDDNAM >", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamGreaterThanOrEqualTo(String value) {
            addCriterion("TDDNAM >=", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamLessThan(String value) {
            addCriterion("TDDNAM <", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamLessThanOrEqualTo(String value) {
            addCriterion("TDDNAM <=", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamLike(String value) {
            addCriterion("TDDNAM like", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamNotLike(String value) {
            addCriterion("TDDNAM not like", value, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamIn(List<String> values) {
            addCriterion("TDDNAM in", values, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamNotIn(List<String> values) {
            addCriterion("TDDNAM not in", values, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamBetween(String value1, String value2) {
            addCriterion("TDDNAM between", value1, value2, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddnamNotBetween(String value1, String value2) {
            addCriterion("TDDNAM not between", value1, value2, "tddnam");
            return (Criteria) this;
        }

        public Criteria andTddcn1IsNull() {
            addCriterion("TDDCN1 is null");
            return (Criteria) this;
        }

        public Criteria andTddcn1IsNotNull() {
            addCriterion("TDDCN1 is not null");
            return (Criteria) this;
        }

        public Criteria andTddcn1EqualTo(String value) {
            addCriterion("TDDCN1 =", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1NotEqualTo(String value) {
            addCriterion("TDDCN1 <>", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1GreaterThan(String value) {
            addCriterion("TDDCN1 >", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1GreaterThanOrEqualTo(String value) {
            addCriterion("TDDCN1 >=", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1LessThan(String value) {
            addCriterion("TDDCN1 <", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1LessThanOrEqualTo(String value) {
            addCriterion("TDDCN1 <=", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1Like(String value) {
            addCriterion("TDDCN1 like", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1NotLike(String value) {
            addCriterion("TDDCN1 not like", value, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1In(List<String> values) {
            addCriterion("TDDCN1 in", values, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1NotIn(List<String> values) {
            addCriterion("TDDCN1 not in", values, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1Between(String value1, String value2) {
            addCriterion("TDDCN1 between", value1, value2, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn1NotBetween(String value1, String value2) {
            addCriterion("TDDCN1 not between", value1, value2, "tddcn1");
            return (Criteria) this;
        }

        public Criteria andTddcn2IsNull() {
            addCriterion("TDDCN2 is null");
            return (Criteria) this;
        }

        public Criteria andTddcn2IsNotNull() {
            addCriterion("TDDCN2 is not null");
            return (Criteria) this;
        }

        public Criteria andTddcn2EqualTo(String value) {
            addCriterion("TDDCN2 =", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2NotEqualTo(String value) {
            addCriterion("TDDCN2 <>", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2GreaterThan(String value) {
            addCriterion("TDDCN2 >", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2GreaterThanOrEqualTo(String value) {
            addCriterion("TDDCN2 >=", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2LessThan(String value) {
            addCriterion("TDDCN2 <", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2LessThanOrEqualTo(String value) {
            addCriterion("TDDCN2 <=", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2Like(String value) {
            addCriterion("TDDCN2 like", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2NotLike(String value) {
            addCriterion("TDDCN2 not like", value, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2In(List<String> values) {
            addCriterion("TDDCN2 in", values, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2NotIn(List<String> values) {
            addCriterion("TDDCN2 not in", values, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2Between(String value1, String value2) {
            addCriterion("TDDCN2 between", value1, value2, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn2NotBetween(String value1, String value2) {
            addCriterion("TDDCN2 not between", value1, value2, "tddcn2");
            return (Criteria) this;
        }

        public Criteria andTddcn3IsNull() {
            addCriterion("TDDCN3 is null");
            return (Criteria) this;
        }

        public Criteria andTddcn3IsNotNull() {
            addCriterion("TDDCN3 is not null");
            return (Criteria) this;
        }

        public Criteria andTddcn3EqualTo(String value) {
            addCriterion("TDDCN3 =", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3NotEqualTo(String value) {
            addCriterion("TDDCN3 <>", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3GreaterThan(String value) {
            addCriterion("TDDCN3 >", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3GreaterThanOrEqualTo(String value) {
            addCriterion("TDDCN3 >=", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3LessThan(String value) {
            addCriterion("TDDCN3 <", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3LessThanOrEqualTo(String value) {
            addCriterion("TDDCN3 <=", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3Like(String value) {
            addCriterion("TDDCN3 like", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3NotLike(String value) {
            addCriterion("TDDCN3 not like", value, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3In(List<String> values) {
            addCriterion("TDDCN3 in", values, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3NotIn(List<String> values) {
            addCriterion("TDDCN3 not in", values, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3Between(String value1, String value2) {
            addCriterion("TDDCN3 between", value1, value2, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn3NotBetween(String value1, String value2) {
            addCriterion("TDDCN3 not between", value1, value2, "tddcn3");
            return (Criteria) this;
        }

        public Criteria andTddcn4IsNull() {
            addCriterion("TDDCN4 is null");
            return (Criteria) this;
        }

        public Criteria andTddcn4IsNotNull() {
            addCriterion("TDDCN4 is not null");
            return (Criteria) this;
        }

        public Criteria andTddcn4EqualTo(String value) {
            addCriterion("TDDCN4 =", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4NotEqualTo(String value) {
            addCriterion("TDDCN4 <>", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4GreaterThan(String value) {
            addCriterion("TDDCN4 >", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4GreaterThanOrEqualTo(String value) {
            addCriterion("TDDCN4 >=", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4LessThan(String value) {
            addCriterion("TDDCN4 <", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4LessThanOrEqualTo(String value) {
            addCriterion("TDDCN4 <=", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4Like(String value) {
            addCriterion("TDDCN4 like", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4NotLike(String value) {
            addCriterion("TDDCN4 not like", value, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4In(List<String> values) {
            addCriterion("TDDCN4 in", values, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4NotIn(List<String> values) {
            addCriterion("TDDCN4 not in", values, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4Between(String value1, String value2) {
            addCriterion("TDDCN4 between", value1, value2, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andTddcn4NotBetween(String value1, String value2) {
            addCriterion("TDDCN4 not between", value1, value2, "tddcn4");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIsNull() {
            addCriterion("AMDTLR is null");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIsNotNull() {
            addCriterion("AMDTLR is not null");
            return (Criteria) this;
        }

        public Criteria andAmdtlrEqualTo(String value) {
            addCriterion("AMDTLR =", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotEqualTo(String value) {
            addCriterion("AMDTLR <>", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrGreaterThan(String value) {
            addCriterion("AMDTLR >", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrGreaterThanOrEqualTo(String value) {
            addCriterion("AMDTLR >=", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLessThan(String value) {
            addCriterion("AMDTLR <", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLessThanOrEqualTo(String value) {
            addCriterion("AMDTLR <=", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLike(String value) {
            addCriterion("AMDTLR like", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotLike(String value) {
            addCriterion("AMDTLR not like", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIn(List<String> values) {
            addCriterion("AMDTLR in", values, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotIn(List<String> values) {
            addCriterion("AMDTLR not in", values, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrBetween(String value1, String value2) {
            addCriterion("AMDTLR between", value1, value2, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotBetween(String value1, String value2) {
            addCriterion("AMDTLR not between", value1, value2, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andUpddatIsNull() {
            addCriterion("UPDDAT is null");
            return (Criteria) this;
        }

        public Criteria andUpddatIsNotNull() {
            addCriterion("UPDDAT is not null");
            return (Criteria) this;
        }

        public Criteria andUpddatEqualTo(Date value) {
            addCriterion("UPDDAT =", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotEqualTo(Date value) {
            addCriterion("UPDDAT <>", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatGreaterThan(Date value) {
            addCriterion("UPDDAT >", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDDAT >=", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatLessThan(Date value) {
            addCriterion("UPDDAT <", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatLessThanOrEqualTo(Date value) {
            addCriterion("UPDDAT <=", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatIn(List<Date> values) {
            addCriterion("UPDDAT in", values, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotIn(List<Date> values) {
            addCriterion("UPDDAT not in", values, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatBetween(Date value1, Date value2) {
            addCriterion("UPDDAT between", value1, value2, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotBetween(Date value1, Date value2) {
            addCriterion("UPDDAT not between", value1, value2, "upddat");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTSC
     *
     * @mbggenerated do_not_delete_during_merge Sun Nov 21 21:36:06 CST 2010
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
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