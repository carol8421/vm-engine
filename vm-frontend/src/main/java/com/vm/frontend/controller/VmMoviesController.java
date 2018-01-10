package com.vm.frontend.controller;

import com.vm.base.utils.ServiceController;
import com.vm.dao.qo.PageBean;
import com.vm.dao.qo.VmMoviesQueryBean;
import com.vm.frontend.service.inf.VmMoviesService;
import com.vm.dao.validator.group.VmMoviesGroups;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by ZhangKe on 2017/12/12.
 */
@Controller
@RequestMapping("/movie")
@Scope("prototype")
public class VmMoviesController extends ServiceController<VmMoviesService> {

    /**
     * 获取所有的tags分组及其下面的tags
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Object getMovies(@Valid PageBean page,
                     BindingResult result,
                     VmMoviesQueryBean query) throws Exception {

        return response.putData("total", service.getMoviesCount(page, query))
                .putData("list", service.getMovies(page, query));
    }

    /**
     * 获取某个电影的的tags
     *
     * @return
     */
    @RequestMapping(value = "/tag/{movieId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getTagsOfMovie(@PathVariable("movieId") Long movieId) throws Exception {

        return response.putData("list", service.getTagsOfMovie(movieId));
    }

    /**
     * 获取某个电影的信息
     *
     * @param movieId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getMovie(@PathVariable("movieId") Long movieId) throws Exception {
        return response.putData("movie", service.getMovie(movieId));
    }

    /**
     * 获取电影图片
     *
     * @return
     */
    @RequestMapping(value = "/img/{fileId}", method = RequestMethod.GET)
    public void getMovieImg(@PathVariable("fileId") Long fileId, Integer width) throws Exception {
        service.sendMovieImg(fileId, width, getResponse());

    }

    /**
     * 获取电影资源
     *
     * @return
     */
    @RequestMapping(value = "/src/{fileId}", method = RequestMethod.GET)
    public void getMovieSrc(@PathVariable("fileId") Long fileId) throws Exception {
        service.sendMovieSrc(fileId, getResponse());
    }

    /**
     * 获取电影相关电影人:包括导演,演员
     *
     * @return
     */
    @RequestMapping(value = "/filmmaker/{movieId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getMovieFilmmakers(@PathVariable("movieId") Long movieId) throws Exception {

        return response.putData("filmmakers", service.getMovieFilmmakers(movieId));
    }

    /**
     * 获取电影版本:例如高清，超清
     *
     * @return
     */
    @RequestMapping(value = "/version/{movieId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getMovieSrcVersions(@PathVariable("movieId") Long movieId) throws Exception {

        return response.putData("versions", service.getMovieSrcVersions(movieId))
                .putData("posterUrl", service.getMoviePosterUrl(movieId));
    }

    /**
     * 获取与标签相关的电影列表
     *
     * @return
     */
    @RequestMapping(value = "/about/tag", method = RequestMethod.GET)
    public @ResponseBody
    Object getAboutTagsMovies(@Validated(value = VmMoviesGroups.GetAboutTagsMoviesGroup.class) VmMoviesQueryBean query,
                              BindingResult result,
                              @Valid PageBean page,
                              BindingResult result0) throws Exception {
        return response.putData("movies", service.getAboutTagsMovies(page, query));
    }

    /**
     * 获取与电影人相关的电影列表
     *
     * @return
     */
    @RequestMapping(value = "/about/filmmaker", method = RequestMethod.GET)
    public @ResponseBody
    Object getAboutFilmmakersMovies(@Validated(value = VmMoviesGroups.GetAboutFilmmakersMoviesGroup.class) VmMoviesQueryBean query,
                                    BindingResult result,
                                    @Valid PageBean page,
                                    BindingResult result0) throws Exception {
        return response.putData("movies", service.getAboutFilmmakersMovies(page, query));
    }


}

